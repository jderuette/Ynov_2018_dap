package fr.ynov.dap.data.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Message Recipient.
 * @author MBILLEMAZ
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipient {
    /**
     * mail.
     */
  private EmailAddress emailAddress;

    /**
     * @return the emailAddress
     */
    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    /**
     * @param emailAddress the emailAddress to set
     */
    public void setEmailAddress(EmailAddress emailAddress) {
        this.emailAddress = emailAddress;
    }

  
}
