import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        AdjacencyList adjacencyList = Utils.initAdjacencyList();
        int length = adjacencyList.getLength();
        int[][] srcToDest = new int[length + 1][length + 1];
        int[][] neighborMatrix = new int[length + 1][length + 1];
        List<int[]>[][] pathList = new List[length + 1][length + 1];
        Utils.initAdjacencyMatrix(adjacencyList.getAdjacency(), srcToDest, neighborMatrix, pathList);
        List<int[]> circles = CalculateUtils.getRoads(srcToDest, neighborMatrix, pathList);
        List<int[]>[] listsForLength = CalculateUtils.scatterList(circles);
        circles = CalculateUtils.gatherList(listsForLength);
        Utils.outputResult(circles);
    }
}
