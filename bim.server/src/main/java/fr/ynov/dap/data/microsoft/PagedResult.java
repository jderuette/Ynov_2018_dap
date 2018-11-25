package fr.ynov.dap.data.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Paged result.
 * @author MBILLEMAZ
 *
 * @param <T> typeof result
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PagedResult<T> {

    /**
     * Context.
     */
    @JsonProperty("@odata.context")
    private String context;

    /**
     * count.
     */
    @JsonProperty("@odata.count")
    private Integer count;
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
     * @return the context
     */
    public String getContext() {
        return context;
    }

    /**
     * @param context the context to set
     */
    public void setContext(String context) {
        this.context = context;
    }

    /**
     * @return the count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(Integer count) {
        this.count = count;
    }

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