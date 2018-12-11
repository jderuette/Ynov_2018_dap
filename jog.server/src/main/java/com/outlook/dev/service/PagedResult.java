package com.outlook.dev.service;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PagedResult<T> {
	@JsonProperty("@odata.context")
	public String context;
	@JsonProperty("@odata.nextLink")
	private String nextPageLink;
	@JsonProperty("value")
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