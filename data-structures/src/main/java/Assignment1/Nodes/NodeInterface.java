package Assignment1.Nodes;

public interface NodeInterface extends Comparable{

    String getName();

    void setName(String name);

    int getfValue();
    
    String getDescription();

    void setDescription(String description);

    @Override
    int compareTo(Object o);
}
