package com.ynov.dap.model.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class Recipient.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipient {
	
	/** The email address. */
	private EmailAddress emailAddress;

	/**
	 * Gets the email address.
	 *
	 * @return the email address
	 */
	public EmailAddress getEmailAddress() {
		return emailAddress;
	}

	/**
	 * Sets the email address.
	 *
	 * @param emailAddress the new email address
	 */
	public void setEmailAddress(EmailAddress emailAddress) {
		this.emailAddress = emailAddress;
	}
}