package fr.ynov.dap.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Recipient entity.
 * @author thibault
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipient {
    /**
     * Email of recipient.
     */
    private EmailAddress emailAddress;

    /**
     * @return the emailAddress
     */
    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    /**
     * @param emailAddressToSet the emailAddress to set
     */
    public void setEmailAddress(final EmailAddress emailAddressToSet) {
        this.emailAddress = emailAddressToSet;
    }
}
