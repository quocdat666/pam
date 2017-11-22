package bean;

import play.Logger;
import play.libs.Json;

public class PagingInfo {

    private int firstRow = 0;
    private int maxRow = 0;
    private int totalRow = 0;
    private int pageSize = 0;

    public PagingInfo(){}

    public PagingInfo(int maxRow){
        this.maxRow = maxRow;
    }

    public String toJsonString(){
        return Json.toJson(this).toString();
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

    public int getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isMoveNextPage(){
        return ((getFirstRow() + getMaxRow()) >= getTotalRow()) ? false : true;
    }

    public boolean isMovePreviousPage(){
        return ((getFirstRow() - getMaxRow()) < 0) ? false : true;
    }
}
