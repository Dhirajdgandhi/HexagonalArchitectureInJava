package AI.Assignment1.UI;

import AI.Assignment1.Algo.GridWorld;
import AI.Assignment1.Algo.RepeatedAStarSearch;
import AI.Assignment1.Entity.Output;
import AI.Assignment1.Entity.XY;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static AI.Assignment1.Utility.Constants.Constant.INFINITY;

public class Test {

    private static final Logger LOG = LoggerFactory.getLogger(Test.class.getName());
    long startTime, endTime;

    public void startTimer() {
        startTime = System.nanoTime();
    }
    public void endTimer() {
        endTime = System.nanoTime();
    }

    public void run() {
        List<Integer> sumCost = new ArrayList();
        sumCost.add(0);
        int runs = 10;
        IntStream.range(0, runs).forEach(i -> {
            try {
                Output output = createMazeAndSearch();
                sumCost.set(0, (int)sumCost.get(0) + (int)output.getCost());
                LOG.info("\n\n\n");
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        });

        LOG.info("The average cost over {}runs for all the AStar Search is : {}steps", runs, sumCost.get(0) / runs);

    }

    public Output createMazeAndSearch() throws CloneNotSupportedException {
        int MAX_ROW = 50;
        int MAX_COL = 50;

        XY start = new XY(0, 0);
        XY goal = new XY(MAX_ROW - 1, MAX_COL - 1);
        GridWorld gridWorld = new GridWorld(1, MAX_ROW, MAX_COL);

        startTimer();
        gridWorld.generateMaze(INFINITY);
        endTimer();
        LOG.info("{} X {} Maze Generation Took : {} microsecs to be generated", MAX_ROW, MAX_COL, (endTime - startTime) / 1000);

        return repeatedForwardAStarSearch(start, goal, gridWorld);
    }

    public Output repeatedBackwardAStarSearch(XY start, XY goal, GridWorld gridWorld) throws CloneNotSupportedException {
        gridWorld.set(start, 1);
        gridWorld.set(goal, 1);

        gridWorld.printGridWorldState();

        RepeatedAStarSearch repeatedBackwardAStarSearch = new RepeatedAStarSearch(gridWorld, start, goal);
        startTimer();
        long backwardCost = repeatedBackwardAStarSearch.search(true, false);
        endTimer();
        long expandedNodes = repeatedBackwardAStarSearch.getExpandedNodes();
        long runTime = (int) (endTime - startTime) / 1000;
        LOG.info("The Repeated AStar Backward Search took {} microsecs with cost of : {} and expanded : {}nodes", runTime, backwardCost, expandedNodes);

        Output output = new Output();
        output.setCost(backwardCost);
        output.setExpandedNodes(expandedNodes);
        output.setRuntime(runTime);

        return output;
    }

    public Output repeatedAdaptiveAStarSearch(XY start, XY goal, GridWorld gridWorld) throws CloneNotSupportedException {
        gridWorld.set(start, 1);
        gridWorld.set(goal, 1);

        RepeatedAStarSearch repeatedForwardAdaptiveAStarSearch = new RepeatedAStarSearch(gridWorld, start, goal);
        startTimer();
        long adaptiveForwardCost = repeatedForwardAdaptiveAStarSearch.search(false, true);
        endTimer();
        long expandedNodes = repeatedForwardAdaptiveAStarSearch.getExpandedNodes();
        long runTime = (int) (endTime - startTime) / 1000;
        LOG.info("The Repeated Adaptive AStar Forward Search took {} microsecs with cost of : {} and expanded : {}nodes", runTime, adaptiveForwardCost, expandedNodes);

        Output output = new Output();
        output.setCost(adaptiveForwardCost);
        output.setExpandedNodes(expandedNodes);
        output.setRuntime(runTime);

        return output;
    }

    public Output repeatedForwardAStarSearch(XY start, XY goal, GridWorld gridWorld) throws CloneNotSupportedException {

        Output output = new Output();
        LOG.info("****** Repeated Forward A* Search *******");
        gridWorld.set(start, 1);
        gridWorld.set(goal, 1);

        gridWorld.printGridWorldState();
        RepeatedAStarSearch repeatedForwardAStarSearch = new RepeatedAStarSearch(gridWorld, start, goal);
        startTimer();
        long forwardCost = repeatedForwardAStarSearch.search(false, false);
        long expandedNodes = repeatedForwardAStarSearch.getExpandedNodes();
        endTimer();
        long runTime = (int) (endTime - startTime) / 1000;
        LOG.info("The Repeated AStar Forward Search took {} microsecs with cost of : {} and expanded : {}nodes", runTime, forwardCost,expandedNodes);

        output.setCost(forwardCost);
        output.setExpandedNodes(expandedNodes);
        output.setRuntime(runTime);

        return output;
    }
}
