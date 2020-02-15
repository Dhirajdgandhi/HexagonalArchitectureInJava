package AI.Assignment1.Algo;

import AI.Assignment1.Nodes.BlockNode;
import AI.Assignment1.Nodes.NodeBase;
import com.spring.boot.PriorityQueue;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

//import java.util.PriorityQueue;


@Component
public class AStarSearch<T> {
    PriorityQueue<NodeBase> openList = new PriorityQueue<NodeBase>(50);
    LinkedList<NodeBase> closedList = new LinkedList<NodeBase>();

    /**
     * Let's assume we have a GRID WORLD problem
     * */
    static final String START = "S";
    static final String GOAL = "G";
    static final String LEGAL_CELL = "1";
    static final String BLOCKED_CELL = "0";

    /**
     * Get the Optimal Path Cost to the Goal
     * @param environment States the problem in terms of a Collection like a Matrix
     *          or a LinkedList or a HashMap or a composition of all these
     * @return Optimal Cost to the GOAL
     *          -1 : If there's no way to REACH THE GOAL
     * */
    public int getOptimalPathCostToReachGoal(Collection<T> environment, Pair initialCell, Pair goalCell){
        NodeBase node = new BlockNode();
        node.setName("START");
        node.setDescription("The Game begins from this position");
        node.setValue(0);
        openList.insert(node);

        while ( openList.getHighestPriority().getName() == "GOAL" ){

        }

        return -1;
    }




    /**
     * Calculates Manhattan Distance between 2 points on a 2D plane
     * This distance is used for calculating Heuristics
     * */
    public long calculateManhattanDistance(Pair A, Pair B){
        return Math.abs((int)A.getFirst() - (int)B.getFirst())
                + Math.abs((int)A.getSecond() - (int)B.getSecond());
    }

    public long calculateEuclideanDistance(Pair A, Pair B){
        return (long) Math.sqrt(
                Math.pow(Math.abs((int)A.getFirst() - (int)B.getFirst()),2)
                        +
                Math.pow(Math.abs((int)A.getSecond() - (int)B.getSecond()),2)
        );
    }

    public long calculateDiagonalDistance(Pair A, Pair B){
        return Math.max(
                Math.abs((int)A.getFirst() - (int)B.getFirst()),
                Math.abs((int)A.getSecond() - (int)B.getSecond())
        );
    }

    /**
     * @param gridWorld Matrix of block cells
     * @param A Co-ordinates of the cell to be checked
     * @return True if the Cell is Blocked
     * */
    public Boolean isCellBlocked(List<List> gridWorld, Pair A){
        return gridWorld.get((int)A.getFirst()).get((int)A.getSecond()) == BLOCKED_CELL;
    }
}
