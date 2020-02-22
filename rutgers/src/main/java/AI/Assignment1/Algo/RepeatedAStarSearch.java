package AI.Assignment1.Algo;

import Assignment1.Nodes.BlockNode;
import Assignment1.Nodes.NodeBase;
//import com.spring.boot.PriorityQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;

import java.util.*;
import java.util.stream.IntStream;

import static com.spring.boot.MathsCalc.calculateManhattanDistance;

public class RepeatedAStarSearch {

    private static final Logger LOG = LoggerFactory.getLogger(RepeatedAStarSearch.class.getName());
    static final int INFINITY = 999999999;
    static final int BLOCKED_CELL = INFINITY;
    private int MAX_X, MAX_Y;
    private static int MIN_X, MIN_Y = 0;
    private GridWorld<Integer> gridWorld;

    private PriorityQueue<NodeBase> openList;
    private LinkedList closedList;
    private NodeBase startNode;
    private NodeBase goalNode;
    private GridWorld<Integer> visitedGridWorld;
    private GridWorld<Integer> visibleGridWorld;

    public RepeatedAStarSearch() {
    }

    public RepeatedAStarSearch(GridWorld<Integer> gridWorld, Pair<Integer, Integer> initialCell, Pair<Integer, Integer> goalCell) {
        this.MAX_X = gridWorld.rowSize();
        this.MAX_Y = gridWorld.colSize();
        this.gridWorld = gridWorld;

        startNode = new BlockNode();
        startNode.setName("S");
        startNode.setDescription("The Game begins from this position");
        startNode.setgValue(0);
        startNode.setXy(initialCell);

        goalNode = new BlockNode();
        goalNode.setName("G");
        goalNode.setDescription("The Game ends at this position");
        goalNode.setgValue(INFINITY);
        goalNode.setXy(goalCell);
    }

    /** The Repeated AStar Algorithm Main Function
     * */
    public int search(boolean backward){
        // initialize for every search
        this.visitedGridWorld = new GridWorld(0, gridWorld.rowSize(), gridWorld.colSize());
        this.visibleGridWorld = new GridWorld(0, gridWorld.rowSize(), gridWorld.colSize());

        NodeBase currentNode = this.startNode;
        int counter=0;
        int cost=0;

        while (!currentNode.equals(goalNode)){
            counter+=1;

            currentNode.setgValue(0); // Takes no cost to reach where we are
            currentNode.sethValue(calculateManhattanDistance(currentNode.getXy(),goalNode.getXy()));
            currentNode.calculateAndSetFValue();

            goalNode.setgValue(INFINITY);
            goalNode.sethValue(0);
            goalNode.calculateAndSetFValue();
            visitedGridWorld.set(goalNode.getXy(), counter);
            visitedGridWorld.set(currentNode.getXy(), counter);

            openList =  new PriorityQueue<>(50000);
            closedList = new LinkedList<>();

            openList.add(currentNode);

            // See your visible cells and update your Visible World
            for ( Pair<Integer, Integer> neighbour : retrieveNeighbours(currentNode)){
                if (isCellLegal(neighbour)) visibleGridWorld.set(neighbour, gridWorld.get(neighbour));
            }

            computePath(currentNode, counter);

            if (!openList.isEmpty()){
                NodeBase node = goalNode;
                List<Pair<Integer, Integer>> plannedPath = new ArrayList<>();

                do{
                    plannedPath.add(node.getXy());
                    node.getParentNode().setChildNode(node);
                    node = node.getParentNode();
                } while(!node.equals(currentNode)); // Stops at the Current Node
                plannedPath.add(node.getXy());
                Collections.reverse(plannedPath);
                LOG.info("Planned Path for execution is : {}", plannedPath);

                // Execution uses Real Grid World
                LOG.info("Execution Begins");
                while(node.getChildNode()!=null && isCellLegalAndUnBlocked(gridWorld, node.getChildNode().getXy())){
                    node = node.getChildNode();
                    cost+=1;
                    LOG.info("Moved to Cell : {}", node.getXy());
                }
                // Run AStar with currentNode again
                currentNode = node;
            } else{
                LOG.info("NO PATH to target can be found.");
                return -1;
            }
        }
        return cost;
    }

    /** AStar Algorithm to compute Path with Start and Goal node
     * */
    private void computePath(NodeBase startNode,int counter){
        NodeBase currentNode =  startNode;

        while( currentNode!=null && goalNode.getgValue() > currentNode.getfValue()){
            LOG.info("Exploring Node : {} : {}",currentNode.getXy(), currentNode);
            closedList.add(currentNode.getXy());

            for (Pair<Integer, Integer> neighbour : retrieveNeighbours(currentNode)){
                if(isCellLegalAndUnBlocked(visibleGridWorld, neighbour)) {
                    NodeBase neighbourNode = new BlockNode();
                    if(goalNode.getXy().equals(neighbour)){
                        neighbourNode = goalNode;
                    }
                    neighbourNode.setXy(neighbour);

                    if (visitedGridWorld.get(neighbour) < counter) {
                        neighbourNode.setgValue(INFINITY);
                        visitedGridWorld.set(neighbour, counter);
                    }

                    if (neighbourNode.getgValue() > currentNode.getgValue() + 1){// g' > g(s) + c(a,s) // c(a,s) = someGrid.get(currentNode.getXy())
                        neighbourNode.setgValue(currentNode.getgValue() + 1);
                        neighbourNode.setParentNode(currentNode);
                        neighbourNode.sethValue(calculateManhattanDistance(neighbour, goalNode.getXy()));
                        neighbourNode.calculateAndSetFValue();

                        if (openList.contains(neighbourNode)){
                            openList.remove(neighbourNode);// TODO: Improve from Linear Search and  write remove function
                        }
                        LOG.info("Adding Neighbour to Open List : {}",neighbourNode);
                        openList.add(neighbourNode);

                    }
                }
            }
            //Remove he current Node that we explored from top of Open List
            openList.poll();
            // Get the next Top of Open List if it's not empty
            if (!openList.isEmpty()){
                currentNode = openList.peek();
            } else {
                currentNode = null;
            }
            LOG.info("\n\n\n");
        }
    }

    public GridWorld<Integer> getVisibleGridWorld() {
        return visibleGridWorld;
    }

    /** Retrieves Neighbour List
     * */
    private List<Pair<Integer, Integer>> retrieveNeighbours(NodeBase node){
        int x = node.getXy().getFirst();
        int y = node.getXy().getSecond();

        return new ArrayList<>(Arrays.asList(Pair.of(x,y+1), Pair.of(x+1,y), Pair.of(x,y-1), Pair.of(x-1,y)));
    }

    /**
     * Get the Optimal Path Cost to the Goal
     * environment States the problem in terms of a Collection like a Matrix
     *          or a LinkedList or a HashMap or a composition of all these
     * @return Optimal Cost to the GOAL
     *          -1 : If there's no way to REACH THE GOAL
     * */


    private Boolean isCellLegal(Pair<Integer, Integer> cell){
        return cell.getFirst() >= MIN_X  && cell.getFirst() < MAX_X && cell.getSecond() >= MIN_Y && cell.getSecond() < MAX_Y;
    }

    /**
     * Checks if given Cell is blocked
     * @param gridWorld Matrix of block cells
     * @param A Co-ordinates of the cell to be checked
     * @return True if the Cell is Blocked
     * */
    private Boolean isCellLegalAndUnBlocked(GridWorld gridWorldrep, Pair<Integer, Integer> A){
        return isCellLegal(A) && !gridWorldrep.get(A).equals(BLOCKED_CELL);
    }

    public void printPathToGoal(){
        LOG.info("Path to Goal : ");
        NodeBase node = startNode;
        List plannedPath = new ArrayList();
        do{
            plannedPath.add(node.getXy());
            node = node.getChildNode();
        } while(node!=null); // Stops at the Current Node
        LOG.info("Planned Path for execution is : {}", plannedPath);
    }
}
