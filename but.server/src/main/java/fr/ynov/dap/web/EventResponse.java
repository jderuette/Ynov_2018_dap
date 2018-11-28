package fr.ynov.dap.web;

import java.util.Date;

/**
 * The response event.
 * @author thibault
 *
 */
public class EventResponse {
    /**
     * Subject.
     */
    private String subject;
    /**
     * Stard date.
     */
    private Date start;
    /**
     * End date.
     */
    private Date end;
    /**
     * I'm the organize ?
     */
    private boolean organizer;
    /**
     * Status of event.
     */
    private String status;

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }
    /**
     * @param subjectName the subject to set
     */
    public void setSubject(final String subjectName) {
        this.subject = subjectName;
    }
    /**
     * @return the start
     */
    public Date getStart() {
        return start;
    }
    /**
     * @param startDate the start to set
     */
    public void setStart(final Date startDate) {
        this.start = startDate;
    }
    /**
     * @return the end
     */
    public Date getEnd() {
        return end;
    }
    /**
     * @param endDate the end to set
     */
    public void setEnd(final Date endDate) {
        this.end = endDate;
    }
    /**
     * @return the organizer
     */
    public Boolean getOrganizer() {
        return organizer;
    }
    /**
     * @param isOrganizer the organizer to set
     */
    public void setOrganizer(final boolean isOrganizer) {
        this.organizer = isOrganizer;
    }
    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }
    /**
     * @param statusEvent the status to set
     */
    public void setStatus(final String statusEvent) {
        this.status = statusEvent;
    }
}
