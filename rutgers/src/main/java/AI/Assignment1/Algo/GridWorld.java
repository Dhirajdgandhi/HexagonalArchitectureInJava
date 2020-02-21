package AI.Assignment1.Algo;

import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class GridWorld<T> {
    private List<List<T>> gridWorld;

    public GridWorld() {
    }

    public GridWorld(List<List<T>> gridWorld) {
        this.gridWorld = gridWorld;
    }

    public GridWorld(GridWorld<T> gridWorld) {
        this.gridWorld = new ArrayList<>();
        // Deep Copy GridWorld to VisitedWorld
        for( List sublist : gridWorld.getGridWorld()) {
            this.gridWorld.add(new ArrayList(sublist));
        }
    }

    public List<List<T>> getGridWorld() {
        return gridWorld;
    }

    public T get(Pair<Integer, Integer> xy){
        int row = xy.getSecond();
        int col = xy.getFirst();
        return gridWorld.get(row).get(col);
    }

    public void set(Pair<Integer, Integer> xy, T value){
        int row = xy.getSecond();
        int col = xy.getFirst();
        gridWorld.get(row).set(col, value);
    }

    public int rowSize(){
        return this.gridWorld!=null ? this.gridWorld.size() : -1;
    }

    public int colSize(){
        return this.gridWorld!=null ? this.gridWorld.get(0).size() : -1;
    }

    public void printGridWorldState(){
        final Integer[] rowList = {null}; //Dummy list

        for(int row=rowSize()-1;row>=0;row--){
            rowList[0] = row;
            IntStream.range(0, colSize()).forEach(
                    col -> {
                        System.out.print("----");
                    }
            );
            System.out.println("");
            IntStream.range(0, colSize()).forEach(
                    col -> {
                        System.out.print(" " +  get(Pair.of(col,rowList[0])) + " |");
                    }
            );
            System.out.println("");
        }
    }

}
