package AI.Assignment1.Entity;

import org.springframework.data.util.Pair;

import java.util.Objects;

public class XY {
    Pair<Integer, Integer> xy;

    public XY() {
    }

    public XY(Pair<Integer, Integer> xy) {
        this.xy = xy;
    }

    public XY(int x, int y) {
        this.xy = Pair.of(x,y);
    }

    public Pair<Integer, Integer> getXy() {
        return xy;
    }

    public int getX() {
        return xy.getFirst();
    }

    public int getY() {
        return xy.getSecond();
    }

    public void setXy(Pair<Integer, Integer> xy) {
        this.xy = xy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        XY xy1 = (XY) o;
        return Objects.equals(xy, xy1.xy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xy);
    }
}
