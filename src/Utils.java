import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {
    private static String path = "test_data.txt";
    private static String outputPath = "result_data.txt";

    static AdjacencyList initAdjacencyList() throws IOException {
        Map<Integer, List<Integer>> adjacency = new HashMap<>();
        int length = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)),
                "UTF-8"));
        String lineTxt;
        while ((lineTxt = br.readLine()) != null) {//数据以逗号分隔
            String[] s = lineTxt.split(",");
            int[] records = new int[2];
            records[0] = Integer.parseInt(s[0]);
            records[1] = Integer.parseInt(s[1]);
            length = Math.max(length, Math.max(records[0], records[1]));
            //src -> dest
            if (!adjacency.containsKey(records[0]))
                adjacency.put(records[0], new ArrayList<>());
            List<Integer> afterNodes = adjacency.get(records[0]);
            afterNodes.add(records[1]);
        }
        br.close();
        return new AdjacencyList(length, adjacency);
    }

    static void initAdjacencyMatrix(Map<Integer, List<Integer>> adjacency,
                                    int[][] srcToDest, int[][] neighborMatrix, List<int[]>[][] pathList) {
        adjacency.forEach((key, destList) -> {
            int src = key;
            destList.forEach((Integer dest) -> {
                srcToDest[src][dest] = 1;
                neighborMatrix[src][dest] = 1;
                // path数组的最后一位表示当前路径长度
                if (src <= dest) {
                    int[] path = new int[9];
                    path[0] = src;
                    path[1] = dest;
                    path[path.length - 1] = 1;
                    if (pathList[src][dest] == null)
                        pathList[src][dest] = new ArrayList<>();
                    pathList[src][dest].add(path);
                }
            });
        });
    }

    static void outputResult(List<int[]> circles) throws IOException {
        File file = new File(outputPath);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream output = new FileOutputStream(file);
        output.write(String.valueOf(circles.size()).getBytes());
        output.write('\n');
        for (int[] circle : circles) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < circle.length && circle[i] != 0; i++) {
                builder.append(circle[i]).append(',');
            }
            builder.deleteCharAt(builder.length() - 1);
            builder.append('\n');
            output.write(builder.toString().getBytes());
        }
        output.close();
    }
}
