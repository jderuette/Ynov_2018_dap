package fr.ynov.dap.microsoft.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author Dom
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipient {
    /**
     *
     */
    private EmailAddress emailAddress;

    /**
     *
     * @return .
     */
    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    /**
     *
     * @param mEmailAddress .
     */
    public void setEmailAddress(final EmailAddress mEmailAddress) {
        this.emailAddress = mEmailAddress;
    }
}
