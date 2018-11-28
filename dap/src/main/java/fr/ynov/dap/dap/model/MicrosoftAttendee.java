package fr.ynov.dap.dap.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author David_tepoche
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MicrosoftAttendee {
    /**
     * the type.
     */
    private String type;

    /**
     * Current attendee status.
     */
    private MicrosoftAttendeeStatus status;

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
     * @param typee the type to set
     */
    public void setType(final String typee) {
        this.type = typee;
    }

    /**
     * @return the status
     */
    public MicrosoftAttendeeStatus getStatus() {
        return status;
    }

    /**
     * @param statuss the status to set
     */
    public void setStatus(final MicrosoftAttendeeStatus statuss) {
        this.status = statuss;
    }

    /**
     * @return the emailAddress
     */
    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    /**
     * @param emailAdrss the emailAddress to set
     */
    public void setEmailAddress(final EmailAddress emailAdrss) {
        this.emailAddress = emailAdrss;
    }

}
