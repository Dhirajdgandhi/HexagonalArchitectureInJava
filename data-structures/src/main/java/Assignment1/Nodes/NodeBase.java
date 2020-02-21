package Assignment1.Nodes;

import org.springframework.data.util.Pair;

public class NodeBase implements NodeInterface, Comparable {

    private String name = "";
    private String description = "";
    private int fValue = 0;
    private int hValue = 1000000000; // Heuristic Cost
    private int gValue = 0; // Path Cost till this Cell
    private Pair<Integer, Integer> xy; // Grid cell co-ordinates
    private NodeBase parentNode;


    private NodeBase childNode;

    public Pair<Integer, Integer> getXy() {
        return xy;
    }

    public void setXy(Pair<Integer, Integer> xy) {
        this.xy = xy;
    }

    public int gethValue() {
        return hValue;
    }

    public void sethValue(int hValue) {
        this.hValue = hValue;
    }

    public int getgValue() {
        return gValue;
    }

    public void setgValue(int gValue) {
        this.gValue = gValue;
    }

    public NodeBase getParentNode() {
        return parentNode;
    }

    public void setParentNode(NodeBase parentNode) {
        this.parentNode = parentNode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getfValue() {
        return fValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void calculateAndSetFValue(){
        this.fValue = this.gValue + this.hValue;
    }

    @Override
    public String toString() {
        return "NodeBase{" +
                "name='" + name + '\'' +
                ", fValue=" + fValue +
                ", xy=" + xy +
                ", hValue=" + hValue +
                ", gValue=" + gValue +
                ", parentNode=" + parentNode +
                '}';
    }

    @Override
    public int compareTo(Object o) {

        return Integer.compare(this.getfValue(), ((NodeBase)o).getfValue());
    }

    @Override
    public boolean equals(Object obj) {
        return getXy().equals(((NodeBase)obj).getXy());
    }


    public NodeBase getChildNode() {
        return childNode;
    }

    public void setChildNode(NodeBase childNode) {
        this.childNode = childNode;
    }
}
