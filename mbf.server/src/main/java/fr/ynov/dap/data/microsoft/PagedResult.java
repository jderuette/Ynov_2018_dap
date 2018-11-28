package fr.ynov.dap.data.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PagedResult<T> {
    @JsonProperty("@odata.nextLink")
    private String nextPageLink;
    private T[] value;

    public final String getNextPageLink() {
        return nextPageLink;
    }
    public final void setNextPageLink(String nextPageLink) {
        this.nextPageLink = nextPageLink;
    }
    public final T[] getValue() {
        return value;
    }
    public final void setValue(T[] value) {
        this.value = value;
    }
}