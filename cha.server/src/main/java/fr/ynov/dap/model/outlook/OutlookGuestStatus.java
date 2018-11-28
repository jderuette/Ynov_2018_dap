package fr.ynov.dap.model.outlook;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import fr.ynov.dap.model.enumeration.GuestStatusEventEnum;

/**
 * The Class OutlookGuestStatus.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutlookGuestStatus {

    /** The response. */
    private String response;

    /**
     * Store response time.
     */
    private Date time;

    /**
     * Gets the reponse.
     *
     * @return the response
     */
    public String getReponse() {
        return response;
    }

    /**
     * Sets the reponse.
     *
     * @param val the response to set
     */
    public void setReponse(final String val) {
        this.response = val;
    }

    /**
     * Gets the time.
     *
     * @return the time
     */
    public Date getTime() {
        return time;
    }

    /**
     * Sets the time.
     *
     * @param val the time to set
     */
    public void setTime(final Date val) {
        this.time = val;
    }

    /**
     * Get the current status.
     * @return Current status
     */
    public GuestStatusEventEnum getEventStatus() {

        if (getReponse().isEmpty() || getReponse() == null) {
            return GuestStatusEventEnum.UNKNOWN;
        }

        switch (getReponse().toLowerCase()) {
        case "accepted":
        	return GuestStatusEventEnum.ACCEPTED;
        case "tentativelyaccepted":
        	return GuestStatusEventEnum.TENTATIVE;
        case "declined":
        	return GuestStatusEventEnum.DECLINED;
        case "none":
        	return GuestStatusEventEnum.UNKNOWN;
        case "organizer":
            return GuestStatusEventEnum.OWNER;
        case "notresponded":
            return GuestStatusEventEnum.NOT_ANSWERED;
        default:
            return GuestStatusEventEnum.UNKNOWN;
        }

    }

}
