package fr.ynov.dap.dap.data.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import fr.ynov.dap.dap.data.microsoft.model.EmailAddress;

/**
 * The Class Recipient.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipient {

	/**
	 * Instantiates a new recipient.
	 */
	public Recipient() {
	}

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
	 * @param emailAddress
	 *            the new email address
	 */
	public void setEmailAddress(EmailAddress emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * Instantiates a new recipient.
	 *
	 * @param emailAddress
	 *            the email address
	 */
	public Recipient(EmailAddress emailAddress) {
		this.emailAddress = emailAddress;
	}

}