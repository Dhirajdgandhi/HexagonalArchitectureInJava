package AI.Assignment1;

import AI.Assignment1.Algo.GridWorld;
import AI.Assignment1.Algo.RepeatedAStarSearch;
import Assignment1.Nodes.NodeBase;
import Assignment1.Nodes.NodeInterface;
import Assignment1.Nodes.NumberNode;
import com.spring.boot.PriorityQueue;
import com.spring.boot.Sort;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplicationRepeatedAStar {

    private static final Logger LOG = LoggerFactory.getLogger(TestApplicationRepeatedAStar.class.getName());

    //    @Autowired
    Sort<NodeInterface> sort;

    static final int INFINITY = 999999999;
    static final int BLOCKED_CELL = INFINITY;
//    @Test
//    public void whenUnSortedArray_thenReturnSortedArray(){
//
//        ArrayList A = new ArrayList(Arrays.asList(5,4,3,2,1));
//        ArrayList sortedA = new ArrayList(Arrays.asList(1,2,3,4,5));
//
//        assertTrue(sort.sortArray(A).equals(sortedA));
//    }

//    @Test
    public void whenUnSortedArray_thenReturnSortedArray_Node(){

        NodeBase N1 = new NumberNode();
        NodeBase N2 = new NumberNode();
        NodeBase N3 = new NumberNode();
        NodeBase N4 = new NumberNode();
        NodeBase N5 = new NumberNode();
//        N1.setfValue(1);
//        N2.setfValue(2);
//        N3.setfValue(3);
//        N4.setfValue(4);
//        N5.setfValue(5);

        ArrayList<NodeInterface> A = new ArrayList(Arrays.asList(N5,N4,N3,N2,N1));
        ArrayList<NodeInterface> sortedA = new ArrayList(Arrays.asList(N1,N2,N3,N4,N5));

//        assertTrue(sort.sortArray(A,(Comparator)new NodeBase.NodeBaseComparator()).equals(sortedA));
    }

//    @Test
    public void whenPriorityQueue_thenDeleteHighestPriority(){

        NodeBase N1 = new NumberNode();
        NodeBase N2 = new NumberNode();
        NodeBase N3 = new NumberNode();
        NodeBase N4 = new NumberNode();
        NodeBase N5 = new NumberNode();
//        N1.setfValue(1);
//        N2.setfValue(2);
//        N3.setfValue(3);
//        N4.setfValue(4);
//        N5.setfValue(5);

        ArrayList<NodeBase> sortedA = new ArrayList(Arrays.asList(N4,N3,N2,N1));

        PriorityQueue<NodeBase> priorityQueue = new PriorityQueue();
        priorityQueue.insert(N2);
        priorityQueue.insert(N1);
        priorityQueue.insert(N5);
        priorityQueue.insert(N3);
        priorityQueue.insert(N4);

        assertTrue(N5.equals(priorityQueue.getHighestPriority()));

        priorityQueue.returnAndDeleteHighestPriority();
//        assertTrue(priorityQueue.getQueue().equals(sortedA));

        assertTrue(N4.equals(priorityQueue.getHighestPriority()));

    }

    @Test
    public void getOptimalCost(){

        List<List> gridWorld = new ArrayList<>();

        gridWorld.add( Arrays.asList(1,1,1,BLOCKED_CELL,1) );
        gridWorld.add( Arrays.asList(1,1,BLOCKED_CELL,BLOCKED_CELL,1) );
        gridWorld.add( Arrays.asList(1,1,BLOCKED_CELL,BLOCKED_CELL,1) );
        gridWorld.add( Arrays.asList(1,1,BLOCKED_CELL,1,1) );
        gridWorld.add( Arrays.asList(1,1,1,1,1) );

//        RepeatedAStarSearch.printGridWorldState(gridWorld);
        System.out.println("****** Repeated Forward A* Search *******");
//        assert(new RepeatedAStarSearch(new GridWorld(gridWorld), Pair.of(2,0), Pair.of(4,0)).search()==12);
        System.out.println();
//
//        gridWorld = new ArrayList<>();
//
//        gridWorld.add( Arrays.asList(1,1,1,BLOCKED_CELL,1) );
//        gridWorld.add( Arrays.asList(1,1,BLOCKED_CELL,BLOCKED_CELL,1) );
//        gridWorld.add( Arrays.asList(1,1,BLOCKED_CELL,BLOCKED_CELL,1) );
//        gridWorld.add( Arrays.asList(1,1,BLOCKED_CELL,1,1) );
//        gridWorld.add( Arrays.asList(1,1,1,1,1) );

//        RepeatedAStarSearch.printGridWorldState(gridWorld);
//        System.out.println("****** Repeated Backward A* Search ******");
//        assert(new RepeatedAStarSearch(new GridWorld(gridWorld), Pair.of(4,0),Pair.of(2,0)).search()==-1);
//        System.out.println();
//
//        gridWorld = new ArrayList<>();
//
//        gridWorld.add( Arrays.asList(1,1,1,BLOCKED_CELL,1) );
//        gridWorld.add( Arrays.asList(1,1,BLOCKED_CELL,BLOCKED_CELL,1) );
//        gridWorld.add( Arrays.asList(1,1,BLOCKED_CELL,BLOCKED_CELL,1) );
//        gridWorld.add( Arrays.asList(1,1,BLOCKED_CELL,1,1) );
//        gridWorld.add( Arrays.asList(1,1,BLOCKED_CELL,1,1) );
//
//        System.out.println("****** Repeated Forward A* Search NO PATH*******");
//        assert(new RepeatedAStarSearch(new GridWorld(gridWorld), Pair.of(2,0), Pair.of(4,0)).search()==-1);
//        System.out.println();

    }



    @Test
    public void repeatedForwardAStarSearch(){

        Pair start = Pair.of(2,0);
        Pair goal =  Pair.of(4,0);
        GridWorld gridWorld = new GridWorld(1, 5,5);
        gridWorld.generateMaze(INFINITY);

        System.out.println("****** Repeated Forward A* Search *******");
        gridWorld.set(start, 1);
        gridWorld.set(goal, 1);

        gridWorld.printGridWorldState();

        RepeatedAStarSearch repeatedAStarSearch = new RepeatedAStarSearch(gridWorld, start, goal);
        int forwardCost = repeatedAStarSearch.search(false);
        LOG.info("Cost for Forward Search is : {}", forwardCost);
        repeatedAStarSearch.printPathToGoal();

        int backwardCost = repeatedAStarSearch.search(true);
        LOG.info("Cost for Backward Search is : {}", backwardCost);
        repeatedAStarSearch.printPathToGoal();
    }


}
