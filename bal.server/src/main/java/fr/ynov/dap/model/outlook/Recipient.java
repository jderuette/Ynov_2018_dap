package fr.ynov.dap.model.outlook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipient {

    private EmailAddress emailAddress;

    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(final EmailAddress val) {
        this.emailAddress = val;
    }

}
