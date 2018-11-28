package fr.ynov.dap.dap.data.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class EmailAddress.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailAddress {
	private String name;
	private String address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}