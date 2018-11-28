package fr.ynov.dap.dap.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author David_tepoche
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MicrosoftAttendeeStatus {

    /**
     * Current user status. e.g. None, Organizer, TentativelyAccepted, Accepted,
     * Declined, NotResponded
     */
    private String response;

    /**
     * Store response time.
     */
    private Date time;

    /**
     * @return the response
     */
    public String getResponse() {
        return response;
    }

    /**
     * @param resp the response to set
     */
    public void setResponse(final String resp) {
        this.response = resp;
    }

    /**
     * @return the time
     */
    public Date getTime() {
        return time;
    }

    /**
     * @param timee the time to set
     */
    public void setTime(final Date timee) {
        this.time = timee;
    }

}
