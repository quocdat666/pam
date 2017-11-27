package models.filter;

import io.ebean.PagedList;

import java.util.List;
import java.util.concurrent.Future;

public class PagedListImp<T> implements PagedList<T> {
    private int pageIndex;
    private int pageSize=10;

    @Override
    public void loadCount() {

    }

    @Override
    public Future<Integer> getFutureCount() {
        return null;
    }

    @Override
    public List<T> getList() {
        return null;
    }

    @Override
    public int getTotalCount() {
        return 0;
    }

    @Override
    public int getTotalPageCount() {
        return 0;
    }

    public void setPageSize(int pageSize){
        this.pageSize=pageSize;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public int getPageIndex() {
        return this.pageIndex;
    }

    public void setPageIndex(int pageIndex){
        this.pageIndex=pageIndex;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public boolean hasPrev() {
        return false;
    }

    @Override
    public String getDisplayXtoYofZ(String to, String of) {
        return null;
    }
}
