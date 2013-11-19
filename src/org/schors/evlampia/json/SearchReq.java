package org.schors.evlampia.json;

public class SearchReq {
    private String toSearch;
    private int count;
    private int start;

    public SearchReq() {
    }

    public SearchReq(String toSearch, int count, int start) {
        this.toSearch = toSearch;
        this.count = count;
        this.start = start;
    }

    public String getToSearch() {
        return toSearch;
    }

    public void setToSearch(String toSearch) {
        this.toSearch = toSearch;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    @Override
    public String toString() {
        return "SearchReq{" +
                "toSearch='" + toSearch + '\'' +
                ", count=" + count +
                ", start=" + start +
                '}';
    }
}
