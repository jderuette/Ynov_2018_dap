package fr.ynov.dap.microsoft.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PagedResult<T> {
    @JsonProperty("@odata.nextLink")
    private String nextPageLink;
    @JsonProperty("value")
    private T[] value;
    @JsonProperty("@odata.context")
    private String context;

    public String getNextPageLink() {
        return nextPageLink;
    }

    public void setNextPageLink(String nextPageLink) {
        this.nextPageLink = nextPageLink;
    }

    public T[] getValue() {
        return value;
    }

    public void setValue(T[] value) {
        this.value = value;
    }
}