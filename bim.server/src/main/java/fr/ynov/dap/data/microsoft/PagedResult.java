package fr.ynov.dap.data.microsoft;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Paged result.
 * @author MBILLEMAZ
 *
 * @param <T>
 */
public class PagedResult<T> {

    /**
     * Context.
     */
    @JsonProperty("@odata.context")
    private String context;
    /**
     * nextPageLink.
     */
    @JsonProperty("@odata.nextLink")
    private String nextPageLink;
    /**
     * Value.
     */
    private T[] value;

    /**
     * @return the nextPageLink
     */
    public String getNextPageLink() {
        return nextPageLink;
    }

    /**
     * @param nextPageLink the nextPageLink to set
     */
    public void setNextPageLink(String nextPageLink) {
        this.nextPageLink = nextPageLink;
    }

    /**
     * @return the value
     */
    public T[] getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(T[] value) {
        this.value = value;
    }

}