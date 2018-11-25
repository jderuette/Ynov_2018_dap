package fr.ynov.dap.data.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Microsoft event entity
 * @author MBILLEMAZ
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MicrosoftEvent {

    /**
     * Default constructor for serialization.
     */
    public MicrosoftEvent() {
    }

    /**
     * @param newId event id
     * @param newSubject event subject
     * @param newOrganizer event organizer
     * @param newStart start date
     * @param newEnd end date
     */
    public MicrosoftEvent(final String newId, final String newSubject, final Recipient newOrganizer,
            final DateTimeTimeZone newStart, final DateTimeTimeZone newEnd) {
        super();
        this.id = newId;
        this.subject = newSubject;
        this.organizer = newOrganizer;
        this.start = newStart;
        this.end = newEnd;
    }

    /**
     * id.
     */
    private String id;
    /**
     * subject.
     */
    private String subject;
    /**
     * organizer.
     */
    private Recipient organizer;
    /**
     * start date.
     */
    private DateTimeTimeZone start;
    /**
     * end date.
     */
    private DateTimeTimeZone end;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the organizer
     */
    public Recipient getOrganizer() {
        return organizer;
    }

    /**
     * @param organizer the organizer to set
     */
    public void setOrganizer(Recipient organizer) {
        this.organizer = organizer;
    }

    /**
     * @return the start
     */
    public DateTimeTimeZone getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(DateTimeTimeZone start) {
        this.start = start;
    }

    /**
     * @return the end
     */
    public DateTimeTimeZone getEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(DateTimeTimeZone end) {
        this.end = end;
    }

}