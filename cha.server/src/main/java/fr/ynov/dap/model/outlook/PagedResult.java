package fr.ynov.dap.model.outlook;

import java.util.ArrayList;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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
     * @return next page link
     */
    public String getNextPageLink() {
        return nextPageLink;
    }

    /**
     * Set next page link.
     * @param val next page link
     */
    public void setNextPageLink(final String val) {
        this.nextPageLink = val;
    }

    /**
     * Get values.
     * @return values.
     */
    public T[] getValue() {
        return value;
    }

    /**
     * Set values.
     * @param val values.
     */
    public void setValue(final T[] val) {
        this.value = val;
    }

    /**
     * Get current values.
     * @return Values
     */
    public ArrayList<T> toArrayList() {
        return new ArrayList<T>(Arrays.asList(value));
}

}
