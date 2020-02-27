package Assignment1.Nodes;

import org.springframework.data.util.Pair;

public class NodeBase implements Comparable, Cloneable {

    private String name = "";
    private String description = "";
    private int fValue = 0;
    private int hValue = 1000000000; // Heuristic Cost
    private int hnew = 1000000000; // Heuristic Cost
    private int gValue = 0; // Path Cost till this Cell

    private Pair<Integer, Integer> xy; // Grid cell co-ordinates
    private NodeBase parentNode;
    private NodeBase childNode;

    public void calculateAndSetFValue(){
        this.fValue = this.gValue + this.hValue;
    }

    @Override
    public int compareTo(Object o) {
        return Integer.compare(fValue, ((NodeBase)o).fValue);
    }

    @Override
    public NodeBase clone() throws CloneNotSupportedException {
        return (NodeBase)super.clone();
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public int getFValue() {
        return this.fValue;
    }

    public int getHValue() {
        return this.hValue;
    }

    public int getHnew() {
        return this.hnew;
    }

    public int getGValue() {
        return this.gValue;
    }

    public Pair<Integer, Integer> getXy() {
        return this.xy;
    }

    public NodeBase getParentNode() {
        return this.parentNode;
    }

    public NodeBase getChildNode() {
        return this.childNode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFValue(int fValue) {
        this.fValue = fValue;
    }

    public void setHValue(int hValue) {
        this.hValue = hValue;
    }

    public void setHnew(int hnew) {
        this.hnew = hnew;
    }

    public void setGValue(int gValue) {
        this.gValue = gValue;
    }

    public void setXy(Pair<Integer, Integer> xy) {
        this.xy = xy;
    }

    public void setParentNode(NodeBase parentNode) {
        this.parentNode = parentNode;
    }

    public void setChildNode(NodeBase childNode) {
        this.childNode = childNode;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof NodeBase)) return false;
        final NodeBase other = (NodeBase) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$xy = this.getXy();
        final Object other$xy = other.getXy();
        if (this$xy == null ? other$xy != null : !this$xy.equals(other$xy)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof NodeBase;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $xy = this.getXy();
        result = result * PRIME + ($xy == null ? 43 : $xy.hashCode());
        return result;
    }

    public String toString() {
        return "NodeBase(name=" + this.getName() + ", description=" + this.getDescription() + ", fValue=" + this.getFValue() + ", hValue=" + this.getHValue() + ", hnew=" + this.getHnew() + ", gValue=" + this.getGValue() + ", xy=" + this.getXy() + ", parentNode=" + this.getParentNode() + ", childNode=" + this.getChildNode() + ")";
    }
}
