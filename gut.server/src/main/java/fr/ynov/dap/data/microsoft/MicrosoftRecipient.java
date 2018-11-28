package fr.ynov.dap.data.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MicrosoftRecipient {
	@JsonProperty("EmailAddress")
	private MicrosoftEmailAddress emailAddress;

	public MicrosoftEmailAddress getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(MicrosoftEmailAddress emailAddress) {
		this.emailAddress = emailAddress;
	}
}
