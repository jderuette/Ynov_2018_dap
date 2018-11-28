package fr.ynov.dap.model.outlook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OutlookGuest {

    private String type;

    private OutlookGuestStatus status;

    private EmailAddress emailAddress;

    public String getType() {
        return type;
    }


    public void setType(final String val) {
        this.type = val;
    }

    public OutlookGuestStatus getStatus() {
        return status;
    }

    public void setStatus(final OutlookGuestStatus val) {
        this.status = val;
    }

    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(final EmailAddress val) {
        this.emailAddress = val;
}
}
