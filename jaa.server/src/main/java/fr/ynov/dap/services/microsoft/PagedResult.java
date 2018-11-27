package fr.ynov.dap.services.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ResultPaged. Used by the Microsoft API.
 * @param <T> Generic type.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PagedResult<T> {
    /**
     * Number of results.
     */
    @JsonProperty("@odata.count")
    private Integer count;

    /**
     * Count getter.
     * @return the number of results.
     */
    public Integer getCount() {
        return count;
    }

    /**
     * Counter setter.
     * @param c counter
     */
    public void setCount(final Integer c) {
        this.count = c;
    }

    /**
     * Url to the next pagedResult.
     */
    @JsonProperty("@odata.nextLink")
    private String nextPageLink;
    /**
     * Array of value.
     */
    private T[] value;

    /**
     * NextPageLink getter.
     * @return the url of the next page.
     */
    public String getNextPageLink() {
        return nextPageLink;
    }

    /**
     * NextPageLink setter.
     * @param link the url of the next page result.
     */
    public void setNextPageLink(final String link) {
        this.nextPageLink = link;
    }

    /**
     * Value array getter.
     * @return the values (results).
     */
    public T[] getValue() {
        return value;
    }

    /**
     * Value setter.
     * @param v the results.
     */
    public void setValue(final T[] v) {
        this.value = v;
    }
}
