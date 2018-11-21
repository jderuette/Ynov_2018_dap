package fr.ynov.dap.microsoft.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import fr.ynov.dap.model.enumeration.AttendeeEventStatusEnum;
import fr.ynov.dap.utils.StrUtils;

/**
 * Represent an attendee status from Outlook Calendar API.
 * @author Kévin Sibué
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutlookAttendeeStatus {

    /**
     * Current user status. e.g. None, Organizer, TentativelyAccepted, Accepted, Declined, NotResponded
     * See from: https://docs.microsoft.com/en-us/previous-versions/office/office-365-api/api/version-1.0
     * /complex-types-for-mail-contacts-calendar-v1#ResponseStatus
     */
    private String response;

    /**
     * Store response time.
     */
    private Date time;

    /**
     * @return the response
     */
    public String getReponse() {
        return response;
    }

    /**
     * @param val the response to set
     */
    public void setReponse(final String val) {
        this.response = val;
    }

    /**
     * @return the time
     */
    public Date getTime() {
        return time;
    }

    /**
     * @param val the time to set
     */
    public void setTime(final Date val) {
        this.time = val;
    }

    /**
     * Get the current status.
     * @return Current status
     */
    public AttendeeEventStatusEnum getEventStatus() {

        if (StrUtils.isNullOrEmpty(getReponse())) {
            return AttendeeEventStatusEnum.UNKNOWN;
        }

        switch (getReponse().toLowerCase()) {
        case "none":
            return AttendeeEventStatusEnum.UNKNOWN;
        case "organizer":
            return AttendeeEventStatusEnum.OWNER;
        case "tentativelyaccepted":
            return AttendeeEventStatusEnum.TENTATIVE;
        case "accepted":
            return AttendeeEventStatusEnum.ACCEPTED;
        case "declined":
            return AttendeeEventStatusEnum.DECLINED;
        case "notresponded":
            return AttendeeEventStatusEnum.NEEDS_ACTION;
        default:
            return AttendeeEventStatusEnum.UNKNOWN;
        }

    }

}
