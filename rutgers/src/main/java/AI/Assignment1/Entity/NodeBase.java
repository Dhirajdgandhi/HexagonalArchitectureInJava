package AI.Assignment1.Entity;

import org.springframework.data.util.Pair;

public class NodeBase implements Comparable, Cloneable {

    private String name = "", description = "";
    private int fValue = 0, hValue = 0, gValue = 0;
    private Pair<Integer, Integer> xy; // Grid cell co-ordinates
    private NodeBase parentNode, childNode;
    private int visited = 0, visible = 0;

    public NodeBase() {
    }

    public NodeBase(Pair<Integer, Integer> xy) {
        this.xy = xy;
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

    public int getVisited() {
        return visited;
    }

    public void setVisited(int visited) {
        this.visited = visited;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
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

    public void calculateAndSetFValue(){
        this.fValue = this.gValue + this.hValue;
    }

    public NodeBase getLinkForPath(boolean backward){
        // For forward use ChildNode connections
        // For backward use ParentNode connections
        return !backward ? childNode : parentNode;
    }

    @Override
    public int compareTo(Object o) {
        NodeBase node = (NodeBase)o;
//        if(fValue == node.getFValue()){
//            return Integer.compare(node.getGValue(), gValue );
//        }
        return Integer.compare(fValue, node.getFValue());
    }

    @Override
    public NodeBase clone() throws CloneNotSupportedException {
        return (NodeBase)super.clone();
    }

    public boolean equals(final Object o) {
        final NodeBase other = (NodeBase) o;
        return this.getXy().equals(other.getXy());
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $xy = this.getXy();
        result = result * PRIME + ($xy == null ? 43 : $xy.hashCode());
        return result;
    }

//    public String toString() {
//        return "NodeBase(name=" + this.getName() + ", description=" + this.getDescription() + ", fValue=" + this.getFValue() + ", hValue=" + this.getHValue() + ", gValue=" + this.getGValue() + ", xy=" + this.getXy() + ", parentNode=" + this.getParentNode() + ", childNode=" + this.getChildNode() + ")";
//    }

    @Override
    public String toString() {
        Pair parent = null;
        if(getParentNode()!=null){
            parent = getParentNode().getXy();
        }
        return "Node :"+getXy()+ " Parent:"+ parent;
    }

}
