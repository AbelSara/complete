import java.util.List;

public class SrcToDestInfo {
    private int[][] srcToDest;
    private List<int[]>[][] pathList;

    public SrcToDestInfo(int[][] srcToDest, List<int[]>[][] pathList) {
        this.srcToDest = srcToDest;
        this.pathList = pathList;
    }

    public int[][] getSrcToDest() {
        return srcToDest;
    }

    public void setSrcToDest(int[][] srcToDest) {
        this.srcToDest = srcToDest;
    }

    public List<int[]>[][] getPathList() {
        return pathList;
    }

    public void setPathList(List<int[]>[][] pathList) {
        this.pathList = pathList;
    }
}
