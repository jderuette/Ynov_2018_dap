package fr.ynov.dap.models.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.api.services.people.v1.model.EmailAddress;

/**
 * MicrosoftRecipient
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MicrosoftRecipient {

    /**
     * emailAddress
     */
    private EmailAddress emailAddress;

    /**
     * Get email address
     *
     * @return EmailAddress
     */
    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    /**
     * Set email address
     *
     * @param emailAddress EmailAddress
     */
    public void setEmailAddress(EmailAddress emailAddress) {
        this.emailAddress = emailAddress;
    }
}
