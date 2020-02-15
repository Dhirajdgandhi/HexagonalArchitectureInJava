package AI.Assignment1.Nodes;

public class BlockNode extends NodeBase {

    long H = (long)1000000000; // Heuristic Cost till
    long G = 0; // Path Cost till this Cell

    // The value will be our f Value
    public long getFValue() {
        return super.getValue();
    }

    static int MAX_X = 10;
    static int MAX_Y = 10;
    static int MIN_X = 0;
    static int MIN_Y = 0;

    Boolean isGoDownLegal(int x){
        x--;
        return MIN_X <= x;
    }

    Boolean isGoUpLegal(int x){
        x++;
        return x <= MAX_X;
    }

    Boolean isGoRightLegal(int y){
        y++;
        return y <= MAX_Y;
    }

    Boolean isGoLeftLegal(int y){
        y--;
        return MIN_Y <= y;
    }

    Boolean isGoLeftTopLegal(int x, int y){
        x++;y--;
        return x <= MAX_X && MIN_Y <= y;
    }

    Boolean isGoLeftDownLegal(int x, int y){
        x--;y--;
        return MIN_X <= x && MIN_Y <= y;
    }

    Boolean isGoRightTopLegal(int x, int y){
        x++; y++;
        return x <= MAX_X && y <= MAX_Y;
    }

    Boolean isGoRightDownLegal(int x, int y){
        x--; y++;
        return MIN_X <= x && y <= MAX_Y;
    }

}
