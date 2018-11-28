package fr.ynov.dap.dap.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author David_tepoche
 *
 * @param <T>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PagedResult<T> {

    /**
     * Link to next page.
     */
    @JsonProperty("@odata.nextLink")
    private String nextPageLink;

    /**
     * Values.
     */
    private T[] value;

    /**
     * Get Next page.
     *
     * @return next page link
     */
    public String getNextPageLink() {
        return nextPageLink;
    }

    /**
     * Set next page link.
     *
     * @param val next page link
     */
    public void setNextPageLink(final String val) {
        this.nextPageLink = val;
    }

    /**
     * Get values.
     *
     * @return values.
     */
    public T[] getValue() {
        return value;
    }

    /**
     * Set values.
     *
     * @param val values.
     */
    public void setValue(final T[] val) {
        this.value = val;
    }

}
