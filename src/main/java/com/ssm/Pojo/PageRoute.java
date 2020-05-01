package com.ssm.Pojo;

import java.io.Serializable;
import java.util.List;

public class PageRoute implements Serializable {
    private int totalCount ;//总记录数 数据库给的
    private int totalPage;//总页数     计算的
    private int currentPage;//当前页码  前端给的
    private int PageSize;//每页显示的条数  前端给的
    private List<Route> list; //每页显示的数据 数据库给的

    public PageRoute(int totalCount, int totalPage, int currentPage, int pageSize, List<Route> list) {
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
        PageSize = pageSize;
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageRoute{" +
                "totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", currentPage=" + currentPage +
                ", PageSize=" + PageSize +
                ", list=" + list +
                '}';
    }

    public PageRoute() {
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return PageSize;
    }

    public void setPageSize(int pageSize) {
        PageSize = pageSize;
    }

    public List<Route> getList() {
        return list;
    }

    public void setList(List<Route> list) {
        this.list = list;
    }
}
