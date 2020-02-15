package AI.Assignment1.Nodes;

import java.util.Comparator;

public class NodeBase implements NodeInterface {
    private String name = null;
    private int value = -1;
    private String description = "No description for this Node";

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public int getValue() {
        return value;
    }


    public void setValue(int value) {
        this.value = value;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Node{" +
//                "name='" + name + '\'' +
                "value=" + value +
//                ", description='" + description + '\'' +
                '}';
    }

    public static class NodeBaseComparator implements Comparator<NodeBase>{

        @Override
        public int compare(NodeBase n1, NodeBase n2) {
            return Integer.compare(n2.getValue(), n1.getValue());
        }
    }
}
