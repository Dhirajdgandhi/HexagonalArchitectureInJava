package AI.Assignment1.Entity;

public class Output {
    long cost;
    long runtime;
    long expandedNodes;

    public Output() {
    }

    public Output(long cost, long runtime, long expandedNodes) {
        this.cost = cost;
        this.runtime = runtime;
        this.expandedNodes = expandedNodes;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public long getRuntime() {
        return runtime;
    }

    public void setRuntime(long runtime) {
        this.runtime = runtime;
    }

    public long getExpandedNodes() {
        return expandedNodes;
    }

    public void setExpandedNodes(long expandedNodes) {
        this.expandedNodes = expandedNodes;
    }
}
