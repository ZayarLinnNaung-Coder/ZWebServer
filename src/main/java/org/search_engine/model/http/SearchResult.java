package org.search_engine.model.http;

import java.util.List;

/**
 * GOOGLE API SEARCH_RESULT
 * */
public class SearchResult {
    private HttpQuery queries;
    private List<ResponseItem> items;

    public List<ResponseItem> getItems() {
        return items;
    }

    public void setItems(List<ResponseItem> items) {
        this.items = items;
    }
}
