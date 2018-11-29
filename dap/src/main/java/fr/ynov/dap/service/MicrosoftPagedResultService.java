package fr.ynov.dap.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
/**
 * Organise les liens entre certaines pages.
 * @author abaracas
 *
 * @param <T> classe
 */
public class MicrosoftPagedResultService<T> {
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
	@JsonProperty("@odata.nextLink")
	private String nextPageLink;
	private T[] value;
	
	
}
