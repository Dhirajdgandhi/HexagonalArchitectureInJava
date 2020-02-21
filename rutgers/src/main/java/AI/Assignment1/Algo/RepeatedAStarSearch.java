package AI.Assignment1.Algo;

import Assignment1.Nodes.BlockNode;
import Assignment1.Nodes.NodeBase;
import com.spring.boot.PriorityQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;

import java.util.*;
import java.util.stream.IntStream;

import static com.spring.boot.MathsCalc.calculateManhattanDistance;

public class RepeatedAStarSearch {

    private static final Logger LOG = LoggerFactory.getLogger(RepeatedAStarSearch.class.getName());
    static final int INFINITY = Integer.MAX_VALUE;
    static final int BLOCKED_CELL = INFINITY;
    private int MAX_X, MAX_Y;
    private static int MIN_X, MIN_Y = 0;
    private GridWorld<Integer> gridWorld;

    private PriorityQueue<NodeBase> openList;
    private Map<Pair, Boolean> closedList;
    private NodeBase startNode;
    private NodeBase goalNode;
    private GridWorld<Integer> visitedGridWorld;
    private GridWorld<Integer> visibleGridWorld = new GridWorld<>();

    public RepeatedAStarSearch() {
    }

    public RepeatedAStarSearch(GridWorld<Integer> gridWorld, Pair<Integer, Integer> initialCell, Pair<Integer, Integer> goalCell) {
        this.MAX_X = gridWorld.rowSize();
        this.MAX_Y = gridWorld.colSize();
        this.gridWorld = gridWorld;
        this.visitedGridWorld = new GridWorld(gridWorld);

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
    public int search(){
        NodeBase currentNode = this.startNode;
        int counter=0;

        while (!currentNode.getXy().equals(goalNode.getXy())){
            counter+=1;
            currentNode.setgValue(0); // Takes no cost to reach where we are
            visitedGridWorld.set(currentNode.getXy(), counter);
            openList =  new PriorityQueue<>(50000);
            closedList = new HashMap<>();

            openList.insert(currentNode);

            // See your visible cells and update your Visible World
            for ( Pair<Integer, Integer> neighbour : retrieveNeighbours(currentNode)){
                if (isCellLegal(neighbour)) visibleGridWorld.set(neighbour, gridWorld.get(neighbour));
            }

            computePath(visibleGridWorld, currentNode, counter);

            if (!openList.isEmpty()){
                NodeBase node = goalNode;
                while(!node.equals(currentNode)){
                    node.getParentNode().setChildNode(node);
                    node = node.getParentNode();
                } // Stops at the Current Node
                // Execution
                while(! isCellLegalAndUnBlocked(gridWorld, node.getChildNode().getXy())){
                    node = node.getChildNode();
                }
                // Run AStar with currentNode again
                currentNode = node;
            } else{
                LOG.info("NO PATH to target can be found.");
                return -1;
            }
        }
        return -1;
    }

    /** AStar Algorithm to compute Path with Start and Goal node
     * */
    private void computePath(GridWorld<Integer> visibleGridWorld, NodeBase startNode,int counter){
        NodeBase currentNode =  startNode;

        while( goalNode.getgValue() > currentNode.getfValue()){
            closedList.put(currentNode.getXy(), true);

            for (Pair<Integer, Integer> neighbour : retrieveNeighbours(currentNode)){
                if(!isCellLegalAndUnBlocked(visibleGridWorld, neighbour)) {
                    NodeBase neighbourNode = new BlockNode();
                    neighbourNode.setXy(neighbour);

                    if (visitedGridWorld.get(neighbour) < counter) {
                        neighbourNode.setgValue(INFINITY);
                        visitedGridWorld.set(neighbour, counter);
                    }

                    if (neighbourNode.getgValue() > currentNode.getgValue() + gridWorld.get(currentNode.getXy())){// g' > g(s) + c(a,s)
                        neighbourNode.setgValue(currentNode.getgValue() + gridWorld.get(currentNode.getXy()));
                        neighbourNode.setParentNode(currentNode);
                        neighbourNode.sethValue(calculateManhattanDistance(neighbour, goalNode.getXy()));
                        neighbourNode.calculateAndSetFValue();

                        if (openList.contains(neighbourNode)) openList.remove(neighbourNode);// TODO: Improve from Linear Search and  write remove function
                        LOG.info("Adding Neighbour to Open List : {}",neighbourNode);
                        openList.insert(neighbourNode);

                    }
                }
            }
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
     * @param environment States the problem in terms of a Collection like a Matrix
     *          or a LinkedList or a HashMap or a composition of all these
     * @return Optimal Cost to the GOAL
     *          -1 : If there's no way to REACH THE GOAL
     * */


    private Boolean isCellLegal(Pair<Integer, Integer> cell){
        return cell.getFirst() >= MIN_X  && cell.getFirst() <= MAX_X && cell.getSecond() >= MIN_Y && cell.getSecond() <= MAX_Y;
    }

    /**
     * Checks if given Cell is blocked
     * @param gridWorld Matrix of block cells
     * @param A Co-ordinates of the cell to be checked
     * @return True if the Cell is Blocked
     * */
    private Boolean isCellLegalAndUnBlocked(GridWorld gridWorld, Pair<Integer, Integer> A){
        return isCellLegal(A) && gridWorld.get(A).equals(BLOCKED_CELL);
    }
}
