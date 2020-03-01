package AI.Assignment1.Entity;

public class Output {
    int cost;
    int runtime;
    int expandedNodes;

    public Output() {
    }

    public Output(int cost, int runtime, int expandedNodes) {
        this.cost = cost;
        this.runtime = runtime;
        this.expandedNodes = expandedNodes;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public int getExpandedNodes() {
        return expandedNodes;
    }

    public void setExpandedNodes(int expandedNodes) {
        this.expandedNodes = expandedNodes;
    }
}
