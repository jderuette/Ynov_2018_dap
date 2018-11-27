package fr.ynov.dap.services.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Used by the Microsoft API.
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipient {
    /**
     * Email Address.
     */
    private EmailAddress emailAddress;

    /**
     * Email Address getter.
     * @return the email address.
     */
    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    /**
     * Email Address setter.
     * @param email Email address.
     */
    public void setEmailAddress(final EmailAddress email) {
        this.emailAddress = email;
    }
}
