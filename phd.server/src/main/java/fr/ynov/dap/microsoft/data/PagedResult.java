package fr.ynov.dap.microsoft.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Dom .
 *
 * @param <T> .
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PagedResult<T> {

    /**
    *
    */
    @JsonProperty("@odata.context")
    private String context;

    /**
    *
    */
    @JsonProperty("@odata.count")
    private String count;

    /**
     * @return the context
     */
    public String getContext() {
        return context;
    }

    /**
     * @param mContext the context to set
     */
    public void setContext(final String mContext) {
        this.context = mContext;
    }

    /**
     *
     */
    @JsonProperty("@odata.nextLink")
    private String nextPageLink;
    /**
     *
     */
    private T[] value;

    /**
     *
     * @return .
     */
    public String getNextPageLink() {
        return nextPageLink;
    }

    /**
     *
     * @param mNextPageLink .
     */
    public void setNextPageLink(final String mNextPageLink) {
        this.nextPageLink = mNextPageLink;
    }

    /**
     *
     * @return .
     */
    public T[] getValue() {
        return value;
    }

    /**
     *
     * @param mValue .
     */
    public void setValue(final T[] mValue) {
        this.value = mValue;
    }
}
