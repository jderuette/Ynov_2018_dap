package fr.ynov.dap.microsoft.data;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mon_PC
 * @param <T>
 */
public class PagedResult<T> {
    /**.
     * context
     */
    @JsonProperty("@odata.context")
    private String context;
    /**.
     * page
     */
    @JsonProperty("@odata.nextLink")
    private String nextPageLink;
    /**.
     * propriété value
     */
    private T[] value;

    /**
     * @return context
     */
    public String getContext() {
        return context;
    }

    /**.
     * Set new page link
     * @param newContext correspondant à la nextPage
     */
    public void setContext(final String newContext) {
        this.context = newContext;
    }

    /**
     * @return nextPageLink
     */
    public String getNextPageLink() {
        return nextPageLink;
    }

    /**.
     * Set new page link
     * @param newNextPageLink correspondant à la nextPage
     */
    public void setNextPageLink(final String newNextPageLink) {
        this.nextPageLink = newNextPageLink;
    }

    /**
     * @return value
     */
    public T[] getValue() {
        return value;
    }

    /**.
     * Set new value
     * @param newValue correspondant new value
     */
    public void setValue(final T[] newValue) {
        this.value = newValue;
    }
}
