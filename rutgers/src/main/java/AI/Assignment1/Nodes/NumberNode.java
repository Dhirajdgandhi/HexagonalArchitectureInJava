package AI.Assignment1.Nodes;

public class NumberNode extends NodeBase {

    public NumberNode() {
    }

    public NumberNode(int value) {
        super.setValue(value);
    }

    public NumberNode(String name, int value, String description) {
        super.setName(name);
        super.setDescription(description);
        super.setValue(value);
    }
}
