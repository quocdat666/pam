package bean;

public class PagingInfo {

    private int firstRow = 0;
    private int maxRow = 0;

    public PagingInfo(){}

    public PagingInfo(int maxRow){
        this.maxRow = maxRow;
    }

    public int getFirstRow() {
        return firstRow;
    }

    public void setFirstRow(int firstRow) {
        this.firstRow = firstRow;
    }

    public int getMaxRow() {
        return maxRow;
    }

    public void setMaxRow(int maxRow) {
        this.maxRow = maxRow;
    }
}
