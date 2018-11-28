package fr.ynov.dap.data.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.api.services.people.v1.model.EmailAddress;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipient {
    private EmailAddress emailAddress;

    public final EmailAddress getEmailAddress() {
        return emailAddress;
    }

    public final void setEmailAddress(EmailAddress emailAddress) {
        this.emailAddress = emailAddress;
    }
}
