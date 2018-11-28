package fr.ynov.dap.microsoft.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Mon_PC
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {
    /**.
     * propriété id
     */
    private String id;
    /**.
     * propriété subject
     */
    private String subject;
    /**.
     * propriété organizer
     */
    private Recipient organizer;
    /**.
     * propriété start
     */
    private DateTimeTimeZone start;
    /**.
     * propriété end
     */
    private DateTimeTimeZone end;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**.
     * @param newId set
     */
    public void setId(final String newId) {
        this.id = newId;
    }

    /**
     * @return subject
     */
    public String getSubject() {
        return subject;
    }

    /**.
     * @param newSubject set
     */
    public void setSubject(final String newSubject) {
        this.subject = newSubject;
    }

    /**
     * @return organizer
     */
    public Recipient getOrganizer() {
        return organizer;
    }

    /**.
     * @param newOrganizer set
     */
    public void setOrganizer(final Recipient newOrganizer) {
        this.organizer = newOrganizer;
    }

    /**
     * @return start
     */
    public DateTimeTimeZone getStart() {
        return start;
    }

    /**
     * @param newStart set
     */
    public void setStart(final DateTimeTimeZone newStart) {
        this.start = newStart;
    }

    /**
     * @return end
     */
    public DateTimeTimeZone getEnd() {
        return end;
    }

    /**.
     * @param newEnd set
     */
    public void setEnd(final DateTimeTimeZone newEnd) {
        this.end = newEnd;
    }
}
