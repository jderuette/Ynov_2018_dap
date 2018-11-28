package fr.ynov.dap.microsoft.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Recipient.
 * @author Kévin Sibué
 *
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
