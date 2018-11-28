package fr.ynov.dap.model.outlook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OutlookGuest {
	 /**
     * Current attendee type. e.g. Required, Optional, Resource
     * See from: https://docs.microsoft.com/en-us/previous-versions/office/office-365-api/api/version-1.0
     * /complex-types-for-mail-contacts-calendar-v1#Attendeev10
     */
    private String type;

    /**
     * Current attendee status.
     */
    private OutlookGuestStatus status;

    /**
     * Current attendee information.
     */
    private EmailAddress emailAddress;

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param val the type to set
     */
    public void setType(final String val) {
        this.type = val;
    }

    /**
     * @return the status
     */
    public OutlookGuestStatus getStatus() {
        return status;
    }

    /**
     * @param val the status to set
     */
    public void setStatus(final OutlookGuestStatus val) {
        this.status = val;
    }

    /**
     * @return the emailAddress
     */
    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    /**
     * @param val the emailAddress to set
     */
    public void setEmailAddress(final EmailAddress val) {
        this.emailAddress = val;
}
}
