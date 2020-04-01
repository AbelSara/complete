import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CalculateUtils {
    public static int ITERATION = 7;

    static List<int[]> getRoads(int[][] srcToDest, int[][] neighborMatrix, List<int[]>[][] pathList) {
        for (int i = 0; i < ITERATION; i++) {
            SrcToDestInfo srcToDestInfo = merge(srcToDest, neighborMatrix, pathList);
            srcToDest = srcToDestInfo.getSrcToDest();
            pathList = srcToDestInfo.getPathList();
        }
        List<int[]> resList = new ArrayList<>();
        for (int i = 0; i < pathList.length; i++) {
            if (pathList[i][i] != null) {
                resList.addAll(pathList[i][i]);
            }
        }
        return resList;
    }

    private static SrcToDestInfo merge(int[][] srcToDest, int[][] neighborMatrix, List<int[]>[][] pathList) {
        int len = srcToDest.length;
        int[][] tmp = srcToDest;
        srcToDest = new int[len][len];
        List<int[]>[][] tmpList = pathList;
        pathList = new List[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                // 已经形成环的路径
                if (i == j) {
                    srcToDest[i][j] = tmp[i][j];
                    if (tmpList[i][j] != null && !tmpList[i][j].isEmpty()) {
                        if (pathList[i][j] == null)
                            pathList[i][j] = new ArrayList<>();
                        pathList[i][j].addAll(tmpList[i][j]);
                    }
                }
                // 当前 i -> j 是可达的路径
                else if (tmp[i][j] == 1) {
                    int[] neighbors = neighborMatrix[j];
                    List<int[]> paths = tmpList[i][j];
                    for (int k = 1; k < len; k++) {
                        if (neighbors[k] != 1)
                            continue;
                        srcToDest[i][k] = 1;
                        List<int[]> curPaths = new ArrayList<>();
                        if (paths == null)
                            continue;
                        for (int[] path : paths) {
                            if (path[path.length - 1] + 1 < path.length - 1 && path[path.length - 1] != k
                                    && k >= path[0] && !containsRecord(path, k)) {
                                if (k == i && path[path.length - 1] < 2)
                                    continue;
                                int[] newPath = new int[path.length];
                                for (int idx = 0; idx < path.length; idx++) {
                                    newPath[idx] = path[idx];
                                }
                                newPath[++newPath[newPath.length - 1]] = k;
                                curPaths.add(newPath);
                            }
                        }
                        if (pathList[i][k] == null)
                            pathList[i][k] = new ArrayList<>();
                        pathList[i][k].addAll(curPaths);
                    }
                }
            }
        }
        return new SrcToDestInfo(srcToDest, pathList);
    }

    private static boolean containsRecord(int[] path, int k) {
        int len = path[path.length - 1];
        for (int i = 1; i <= len; i++) {
            if (path[i] == k)
                return true;
        }
        return false;
    }

    static List<int[]>[] scatterList(List<int[]> circles) {
        for (int[] circle : circles) {
            circle[circle[circle.length - 1]] = 0;
        }
        List<int[]>[] listsForLength = new List[ITERATION + 1];
        for (int[] circle : circles) {
            int len = circle[circle.length - 1];
            if (listsForLength[len] == null)
                listsForLength[len] = new ArrayList<>();
            listsForLength[len].add(circle);
        }
        return listsForLength;
    }

    static List<int[]> gatherList(List<int[]>[] listsForLength) {
        List<int[]> circles = new ArrayList<>();
        for (int i = 1; i < listsForLength.length; i++) {
            circles.addAll(gather(listsForLength[i], i));
        }
        return circles;
    }

    private static List<int[]> gather(List<int[]> list, int len) {
        if (list == null)
            return new ArrayList<>();
        for (int i = len; i >= 0; i--) {
            Map<Integer, List<int[]>> dict = new TreeMap<>();
            // scatter
            for (int[] circle : list) {
                if (!dict.containsKey(circle[i]))
                    dict.put(circle[i], new ArrayList<>());
                List<int[]> cs = dict.get(circle[i]);
                cs.add(circle);
            }
            //gather
            list.clear();
            dict.forEach((key, value) -> list.addAll(value));
        }
        return list;
    }
}
