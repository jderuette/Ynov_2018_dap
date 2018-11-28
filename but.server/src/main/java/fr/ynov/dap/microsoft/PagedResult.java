package fr.ynov.dap.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class to help with pagination of microsoft.
 * @author thibault
 *
 * @param <T> the entity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PagedResult<T> {
    /**
     * Next page link.
     */
    @JsonProperty("@odata.nextLink")
    private String nextPageLink;

    /**
     * Total count of entities.
     */
    @JsonProperty("@odata.count")
    private int count;

    /**
     * List of data.
     */
    private T[] value;

    /**
     * @return the nextPageLink
     */
    public String getNextPageLink() {
        return nextPageLink;
    }

    /**
     * @param nextPageLinkToSet the nextPageLink to set
     */
    public void setNextPageLink(final String nextPageLinkToSet) {
        this.nextPageLink = nextPageLinkToSet;
    }

    /**
     * @return the value
     */
    public T[] getValue() {
        return value;
    }

    /**
     * @param valueToSet the value to set
     */
    public void setValue(final T[] valueToSet) {
        this.value = valueToSet;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param countToSet the count to set
     */
    public void setCount(final int countToSet) {
        this.count = countToSet;
    }
}
