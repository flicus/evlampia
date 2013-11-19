package org.schors.evlampia.search;

import java.util.Collection;
import java.util.Collections;

public class SearchResult<T> {
    public final int hits;
    private final Collection<T> items;

    protected SearchResult(int hits, Collection<T> items) {
        this.hits = hits;
        this.items = items;
    }

    public Collection<T> getItems() {
        if (items != null)
            return Collections.unmodifiableCollection(items);
        return null;
    }

    interface IResult<T> {
        SearchResult<T> Result(String story, int count, int start) throws Exception;
    }
    
    
}
