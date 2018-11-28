package fr.ynov.dap.dap.microsoft.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Florian
 * @param <T>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PagedResult<T> {
    /**.
     * Déclaration de nextPageLink
     */
    @JsonProperty("@odata.nextLink")
    private String nextPageLink;
    /**.
     * Déclaration de value
     */
    private T[] value;
    /**.
     * Déclaration du context
     */
    @JsonProperty("@odata.context")
    private String context;
    /**.
     * Déclaration de nbMailNonLus
     */
    @JsonProperty("unreadItemCount")
    private String totalUnreadMails;

    /**
     * @return the nbMailNonLus
     */
    public String getTotalUnreadMails() {
        return totalUnreadMails;
    }

    /**
     * @param theNbMailNonLus the nbMailNonLus to set
     */
    protected void settotalUnreadMails(final String theNbMailNonLus) {
        this.totalUnreadMails = theNbMailNonLus;
    }

    /**
     * @return nextPageLink
     */
    public String getNextPageLink() {
        return nextPageLink;
    }

    /**
     * @param theNextPageLink modification de la valeur
     */
    public void setNextPageLink(final String theNextPageLink) {
        this.nextPageLink = theNextPageLink;
    }

    /**
     * @return value
     */
    public T[] getValue() {
        return value;
    }

    /**
     * @param thevalue modification de la valeur
     */
    public void setValue(final T[] thevalue) {
        this.value = thevalue;
    }

    /**
     * @return context
     */
    public String getContext() {
        return context;
    }

    /**
     * @param theContext Modification de la valeur
     */
    public void setContext(final String theContext) {
        this.context = theContext;
    }
}
