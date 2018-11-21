package fr.ynov.dap.data;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import fr.ynov.dap.model.AttendeeEventStatusEnum;
import fr.ynov.dap.utils.StrUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OutlookAttendeeStatus {

    /**
     * Current user status. e.g. None, Organizer, TentativelyAccepted, Accepted, Declined, NotResponded
     * See from: https://docs.microsoft.com/en-us/previous-versions/office/office-365-api/api/version-1.0
     * /complex-types-for-mail-contacts-calendar-v1#ResponseStatus
     */
    private String response;

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

    public AttendeeEventStatusEnum getEventStatus() {

        if (StrUtils.isNullOrEmpty(getReponse())) {
            return AttendeeEventStatusEnum.UNKNOWN;
        }

        switch (getReponse()) {
        case "None":
            return AttendeeEventStatusEnum.UNKNOWN;
        case "Organizer":
            return AttendeeEventStatusEnum.OWNER;
        case "TentativelyAccepted":
            return AttendeeEventStatusEnum.TENTATIVE;
        case "Accepted":
            return AttendeeEventStatusEnum.ACCEPTED;
        case "Declined":
            return AttendeeEventStatusEnum.DECLINED;
        case "NotResponded":
            return AttendeeEventStatusEnum.NEEDS_ACTION;
        default:
            return AttendeeEventStatusEnum.UNKNOWN;
        }

    }

}
