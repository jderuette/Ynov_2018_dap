package fr.ynov.dap.microsoft.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Mon_PC
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipient {
    /**.
     * Propriété EmailAdresse
     */
    private EmailAddress emailAddress;

    /**
     * @return emailAdress
     */
    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    /**.
     * Set new emailAdress
     * @param newEmailAddress correspondant à la nouvelle adresse mail
     */
    public void setEmailAddress(final EmailAddress newEmailAddress) {
        this.emailAddress = newEmailAddress;
    }
}
