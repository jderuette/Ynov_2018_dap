package fr.ynov.dap.model.outlook;

import java.util.ArrayList;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PagedResult<T> {

    @JsonProperty("@odata.nextLink")
    private String nextPageLink;

    private T[] value;

    public String getNextPageLink() {
        return nextPageLink;
    }

    public void setNextPageLink(final String val) {
        this.nextPageLink = val;
    }

    public T[] getValue() {
        return value;
    }

    public void setValue(final T[] val) {
        this.value = val;
    }

    public ArrayList<T> toArrayList() {
        return new ArrayList<T>(Arrays.asList(value));
	}

}
