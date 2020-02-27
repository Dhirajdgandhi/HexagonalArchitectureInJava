//package AI.Assignment1.Algo;
//
//import Assignment1.Nodes.BlockNode;
//import Assignment1.Nodes.NodeBase;
//import com.spring.boot.PriorityQueue;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.data.util.Pair;
//
//import java.util.*;
//import java.util.stream.IntStream;
//
//import static com.spring.boot.MathsCalc.calculateManhattanDistance;
//
//public class AStarSearch {
//
//    private static final Logger LOG = LoggerFactory.getLogger(AStarSearch.class.getName());
//
//    static int MAX_X, MAX_Y;
//    static int MIN_X, MIN_Y = 0;
//
//    PriorityQueue<NodeBase> openList = new PriorityQueue<>(500);
//    Map<Pair, Boolean> closedList = new HashMap<>();
//
//    static final String START = "S";
//    static final int BLOCKED_CELL = 0;
//
//    /**
//     * Get the Optimal Path Cost to the Goal
//     * @param environment States the problem in terms of a Collection like a Matrix
//     *          or a LinkedList or a HashMap or a composition of all these
//     * @return Optimal Cost to the GOAL
//     *          -1 : If there's no way to REACH THE GOAL
//     * */
//    public int getOptimalPathCostToReachGoal(List<List> environment, Pair<Integer, Integer> initialCell, Pair<Integer, Integer> goalCell){
//        MAX_Y = environment.size() - 1;
//        MAX_X = environment.get(0).size() - 1;
//
//        NodeBase currentNode = new BlockNode();
//        currentNode.setName(START);
//        currentNode.setDescription("The Game begins from this position");
//        currentNode.setGValue(0);
//        currentNode.setParentNode(null);
//        currentNode.setXy(initialCell);
//
//        openList.insert(currentNode);
//        Pair<Integer, Integer> currentCell = initialCell;
//
//        while(!openList.isEmpty()){
//            currentNode = openList.getHighestPriority();
//            currentCell = currentNode.getXy();
//
//            LOG.info("Expanding Node : {}", currentNode);
//            if(closedList.containsKey(currentCell)){ // Visited Node needs to be ignored
//                openList.returnAndDeleteHighestPriority();
//                continue;
//            }
//
//            if(currentCell.equals(goalCell)) break; // Exit if we reached the goal
//
//            int x = currentNode.getXy().getFirst();
//            int y = currentNode.getXy().getSecond();
//
//            List<Pair<Integer, Integer>> neighbourList =
//                    new ArrayList<>(Arrays.asList(Pair.of(x,y+1), Pair.of(x+1,y), Pair.of(x,y-1), Pair.of(x-1,y)));
//
//            for (Pair<Integer, Integer> neighbour : neighbourList) {
//                if(
//                        isCellLegal(neighbour)   // Legal Cell
//                        && !isCellBlocked(environment, neighbour) // Un-blocked Cell
//                        && !closedList.containsKey(neighbour)){ // Un-visited Cell
//                    NodeBase node = new BlockNode();
//                    node.setParentNode(currentNode);
//                    node.setXy(neighbour);
//                    node.setHValue(calculateManhattanDistance(neighbour, goalCell));
//                    node.setGValue(node.getParentNode().getGValue() + (int)environment.get(neighbour.getSecond()).get(neighbour.getFirst()));
//                    node.calculateAndSetFValue();
//                    LOG.info("Adding Child to Open List : {}",node);
//                    openList.insert(node);
//                }
//            }
//            LOG.info("Adding Node to Closed List : {}", currentNode);
//            closedList.put(openList.returnAndDeleteHighestPriority().getXy(), true);
//        }
//
//        // If after exiting while loop, current is not goal then we are not able to reach goal
//        if(!currentCell.equals(goalCell)){
//           return -1;
//        }
//
//        int pathCost = currentNode.getGValue();
//
//        LOG.info("Path Cost to Goal is : {}",pathCost);
//        LOG.info("Path to Goal");
//
//        while(currentNode.getName()!=START){
//            int row = currentNode.getXy().getSecond();
//            int column = currentNode.getXy().getFirst();
//            environment.get(row).set(column,8);
//            currentNode = currentNode.getParentNode();
//        }
//        environment.get(initialCell.getSecond()).set(initialCell.getFirst(),7);
//        environment.get(goalCell.getSecond()).set(goalCell.getFirst(),9);
//        printGridWorldState(environment);
//
//        return pathCost;
//    }
//
//    private Boolean isCellLegal(Pair<Integer, Integer> cell){
//        return cell.getFirst() >= MIN_X  && cell.getFirst() <= MAX_X && cell.getSecond() >= MIN_Y && cell.getSecond() <= MAX_Y;
//    }
//
//
//    public static void printGridWorldState(List<List> gridWorld){
//        //Get row and column count
//        int rows = gridWorld.size();
//        int columns = gridWorld.get(0).size();
//
//        final Integer[] rowList = {null}; //Dummy list
//
//        for(int row=rows-1;row>=0;row--){
//            rowList[0] = row;
//            IntStream.range(0, columns).forEach(
//                    col -> {
//                        System.out.print("----");
//                    }
//            );
//            System.out.println("");
//            IntStream.range(0, columns).forEach(
//                    col -> {
////                        LOG.info("  {} |", gridWorld.get(rowList[0]).get(col));
//                        System.out.print(" " +  gridWorld.get(rowList[0]).get(col) + " |");
//                    }
//            );
//            System.out.println("");
//        }
//    }
//
//    /**
//     * Checks if given Cell is blocked
//     * @param gridWorld Matrix of block cells
//     * @param A Co-ordinates of the cell to be checked
//     * @return True if the Cell is Blocked
//     * */
//    private Boolean isCellBlocked(List<List> gridWorld, Pair<Integer, Integer> A){
//        return isCellLegal(A) && gridWorld.get(A.getSecond()).get(A.getFirst()).equals(BLOCKED_CELL);
//    }
//}
