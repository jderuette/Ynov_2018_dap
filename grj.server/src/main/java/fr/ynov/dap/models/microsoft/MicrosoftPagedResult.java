package fr.ynov.dap.models.microsoft;

import com.fasterxml.jackson.annotation.*;

/**
 * MicrosoftPagedResult
 *
 * @param <T>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MicrosoftPagedResult<T> {

    /**
     * nextPageLink
     */
    @JsonProperty("@odata.nextLink")
    private String nextPageLink;

    /**
     * value
     */
    private T[] value;


    /*
    GETTERS AND SETTERS
     */

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
