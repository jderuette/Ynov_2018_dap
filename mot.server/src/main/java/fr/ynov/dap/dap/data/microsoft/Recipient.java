package fr.ynov.dap.dap.data.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class Recipient.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipient {
	private EmailAddress emailAddress;

	public EmailAddress getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(EmailAddress emailAddress) {
		this.emailAddress = emailAddress;
	}
}
