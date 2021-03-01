package org.jerry.jorm;


import java.util.List;

public class GridJson {
    private int page;//当前页
    private long records;//总记录数
    private long total;//总页数
    private List rows;//返回数据

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public long getRecords() {
        return records;
    }

    public void setRecords(long records) {
        this.records = records;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public GridJson(int page, long records, long total) {
        this.page = page;
        this.records = records;
        this.total = total;
    }

    public GridJson(int page, List rows) {
        this.page = page;
        this.rows = rows;
    }

    public GridJson(int page) {
        this.page = page;
    }

    public GridJson() {
    }

    public GridJson(int page, long records, long size, List rows) {
        this.page = page;
        this.records = records;
        this.total = (long) Math.ceil((double) records / (double) size);
        this.rows = rows;
    }
}
