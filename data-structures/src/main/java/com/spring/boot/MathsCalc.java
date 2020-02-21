package com.spring.boot;

import org.springframework.data.util.Pair;

public abstract class MathsCalc {
    /**
     * Calculates Manhattan Distance between 2 points on a 2D plane
     * This distance is used for calculating Heuristics
     * */
    public static int calculateManhattanDistance(Pair<Integer, Integer> A, Pair<Integer, Integer> B){
        return Math.abs(A.getFirst() - B.getFirst())
                + Math.abs(A.getSecond() - B.getSecond());
    }

    private static long calculateEuclideanDistance(Pair A, Pair B){
        return (long) Math.sqrt(
                Math.pow(Math.abs((int)A.getFirst() - (int)B.getFirst()),2)
                        +
                        Math.pow(Math.abs((int)A.getSecond() - (int)B.getSecond()),2)
        );
    }

    public static long calculateDiagonalDistance(Pair A, Pair B){
        return Math.max(
                Math.abs((int)A.getFirst() - (int)B.getFirst()),
                Math.abs((int)A.getSecond() - (int)B.getSecond())
        );
    }

}
