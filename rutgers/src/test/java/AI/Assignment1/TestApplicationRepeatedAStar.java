package AI.Assignment1;

import AI.Assignment1.Algo.GridWorld;
import AI.Assignment1.Algo.RepeatedAStarSearch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplicationRepeatedAStar {

    private static final Logger LOG = LoggerFactory.getLogger(TestApplicationRepeatedAStar.class.getName());

    static final int INFINITY = 999999999;
    long startTime, endTime;

    public void startTimer(){
        startTime = System.nanoTime();
    }

    public void endTimer(){
        endTime = System.nanoTime();
    }

    @Test
    public void run(){
        List<Integer> sumCost = new ArrayList();
        sumCost.add(0);
        int runs = 100;
        IntStream.range(0,runs).forEach(i -> {
            try {
                int cost = repeatedForwardAStarSearch();
                sumCost.set(0, sumCost.get(0) + cost);
                LOG.info("\n\n\n");
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        });

        LOG.info("The average cost over {}runs for all the AStar Search is : {}steps", runs, sumCost.get(0)/runs);

    }

    public int repeatedForwardAStarSearch() throws CloneNotSupportedException {

        int MAX_ROW=5;
        int MAX_COL=5;

        Pair start = Pair.of(0,0);
        Pair goal =  Pair.of(MAX_ROW-1,MAX_COL-1);
        GridWorld gridWorld = new GridWorld(1, MAX_ROW,MAX_COL);

        startTimer();
        gridWorld.generateMaze(INFINITY);
        endTimer();

        LOG.info("****** Repeated Forward A* Search *******");
        gridWorld.set(start, 1);
        gridWorld.set(goal, 1);

        gridWorld.printGridWorldState();

        LOG.info("{} X {} Maze Generation Took : {} microsecs to be generated", MAX_ROW, MAX_COL, (endTime - startTime)/1000);

        RepeatedAStarSearch repeatedForwardAStarSearch = new RepeatedAStarSearch(gridWorld, start, goal);
        startTimer();
        int forwardCost = repeatedForwardAStarSearch.search(false);
        endTimer();
        LOG.info("The Repeated AStar Forward Search took {} microsecs with cost of : {} and expanded : {}nodes", (endTime - startTime)/1000, forwardCost, repeatedForwardAStarSearch.getExpandedNodes());

        RepeatedAStarSearch repeatedBackwardAStarSearch = new RepeatedAStarSearch(gridWorld, start, goal);
        startTimer();
        int backwardCost = repeatedBackwardAStarSearch.search(true);
        endTimer();
        LOG.info("The Repeated AStar Backward Search took {} microsecs with cost of : {} and expanded : {}nodes", (endTime - startTime)/1000, backwardCost, repeatedForwardAStarSearch.getExpandedNodes());

        if(forwardCost==-1) return repeatedForwardAStarSearch();
        else return forwardCost;
    }

}
