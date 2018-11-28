package dap.client.model.dto;

import java.sql.Timestamp;
import java.util.Date;

/**
 * used to map the respponse from the server.
 *
 * @author David_tepoche
 *
 */
public class EventResponse {

    /**
     * subject of the calendar event.
     */
    private String subject;
    /**
     * start of the event.
     */
    private Timestamp startDate;
    /**
     * end of the event.
     */
    private Timestamp endDate;

    /**
     * status of the event.
     */
    private String status;

    /**
     * status of the user on the event.
     */
    private String personnalStatus;

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subj the subject to set
     */
    public void setSubject(final String subj) {
        this.subject = subj;
    }

    /**
     * @return the personnalStatus
     */
    public String getPersonnalStatus() {
        return personnalStatus;
    }

    /**
     * @param persoStatus the personnalStatus to set
     */
    public void setPersonnalStatus(final String persoStatus) {
        this.personnalStatus = persoStatus;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDateTimestamp the startDate to set
     */
    public void setStartDate(final Timestamp startDateTimestamp) {
        this.startDate = startDateTimestamp;
    }

    /**
     * @param endDateTimeStamp the endDate to set
     */
    public void setEndDate(final Timestamp endDateTimeStamp) {
        this.endDate = endDateTimeStamp;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param statusOfEvent the status to set
     */
    public void setStatus(final String statusOfEvent) {
        this.status = statusOfEvent;
    }

    @Override
    public final String toString() {
        String message = "l'evenement " + this.subject + " commence le " + this.startDate + " et fini le "
                + this.endDate + " ." + "\n Cet evenement est au status " + this.status + " et pour vous il est "
                + this.personnalStatus;

        return message;
    }

}
