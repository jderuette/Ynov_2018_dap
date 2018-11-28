package fr.ynov.dap.dap.data.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class Contact.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact {
	private String id;
	private String givenName;
	private String surname;
	private String companyName;
	private EmailAddress[] emailAddresses;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public EmailAddress[] getEmailAddresses() {
		return emailAddresses;
	}

	public void setEmailAddresses(EmailAddress[] emailAddresses) {
		this.emailAddresses = emailAddresses;
	}
}
