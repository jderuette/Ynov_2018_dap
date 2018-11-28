package com.ynov.dap.model.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class Contact.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact {

	/** The id. */
	private String id;

	/** The given name. */
	private String givenName;

	/** The surname. */
	private String surname;

	/** The company name. */
	private String companyName;

	/** The email addresses. */
	private EmailAddress[] emailAddresses;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * Gets the given name.
	 *
	 * @return the given name
	 */
	public String getGivenName() {
		return givenName;
	}

	/**
	 * Sets the given name.
	 *
	 * @param givenName the new given name
	 */
	public void setGivenName(final String givenName) {
		this.givenName = givenName;
	}

	/**
	 * Gets the surname.
	 *
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Sets the surname.
	 *
	 * @param surname the new surname
	 */
	public void setSurname(final String surname) {
		this.surname = surname;
	}

	/**
	 * Gets the company name.
	 *
	 * @return the company name
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * Sets the company name.
	 *
	 * @param companyName the new company name
	 */
	public void setCompanyName(final String companyName) {
		this.companyName = companyName;
	}

	/**
	 * Gets the email addresses.
	 *
	 * @return the email addresses
	 */
	public EmailAddress[] getEmailAddresses() {
		return emailAddresses;
	}

	/**
	 * Sets the email addresses.
	 *
	 * @param emailAddresses the new email addresses
	 */
	public void setEmailAddresses(final EmailAddress[] emailAddresses) {
		this.emailAddresses = emailAddresses;
	}
}
