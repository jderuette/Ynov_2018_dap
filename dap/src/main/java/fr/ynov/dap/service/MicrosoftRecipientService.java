package fr.ynov.dap.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
/**
 * Permet de récupérer ou modifier l'adresse mail.
 * @author abaracas
 *
 */
public class MicrosoftRecipientService {
	@JsonProperty("EmailAddress")
	private EmailAddressService emailAddress;

	/**
	 * @return the emailAddress
	 */
	public EmailAddressService getEmailAddress() {
	    return emailAddress;
	}

	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(EmailAddressService emailAddress) {
	    this.emailAddress = emailAddress;
	}

	
}
