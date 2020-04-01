import java.util.List;
import java.util.Map;

public class AdjacencyList {
    private int length;
    private Map<Integer, List<Integer>> adjacency;

    public AdjacencyList(int length, Map<Integer, List<Integer>> adjacency) {
        this.length = length;
        this.adjacency = adjacency;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Map<Integer, List<Integer>> getAdjacency() {
        return adjacency;
    }

    public void setAdjacency(Map<Integer, List<Integer>> adjacency) {
        this.adjacency = adjacency;
    }
}
