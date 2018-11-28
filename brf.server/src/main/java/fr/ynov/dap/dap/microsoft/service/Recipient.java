package fr.ynov.dap.dap.microsoft.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.api.services.people.v1.model.EmailAddress;

/**
 * @author Florian
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipient {
    /**.
     * Déclaration de emailAddress
     */
    private EmailAddress emailAddress;

    /**
     * @return emailAddress
     */
    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    /**
     * @param theEmailAddress modification de la valeur
     */
    public void setEmailAddress(final EmailAddress theEmailAddress) {
        this.emailAddress = theEmailAddress;
    }
}
