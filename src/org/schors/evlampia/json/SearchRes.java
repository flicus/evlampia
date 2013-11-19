package org.schors.evlampia.json;

import org.schors.evlampia.search.SearchResult;

public class SearchRes extends Response {
    private SearchResult searchResult;

    public SearchRes() {
    }

    public SearchRes(SearchResult searchResult) {
        this.searchResult = searchResult;
    }

    public SearchResult getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(SearchResult searchResult) {
        this.searchResult = searchResult;
    }

    @Override
    public String toString() {
        return "SearchRes{" +
                "searchResult=" + searchResult +
                '}';
    }
}
