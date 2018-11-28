package fr.ynov.dap.dap.data.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class PagedResult.
 *
 * @param <T> the generic type
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PagedResult<T> {
	@JsonProperty("@odata.nextLink")
	private String nextPageLink;
	private T[] value;

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
