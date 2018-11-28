package fr.ynov.dap.model.outlook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class Recipient.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipient {

    /**
     * Store email address.
     */
    private EmailAddress emailAddress;

    /**
     * Return email address.
     * @return email address.
     */
    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    /**
     * Set email address.
     * @param val email address.
     */
    public void setEmailAddress(final EmailAddress val) {
        this.emailAddress = val;
    }

}
