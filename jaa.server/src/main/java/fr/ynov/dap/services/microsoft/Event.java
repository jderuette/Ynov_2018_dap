package fr.ynov.dap.services.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Event entity used by the Microsoft API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {
    /**
     * Id.
     */
    private String id;
    /**
     * Subject of the event.
     */
    private String subject;
    /**
     * Organizer of the event.
     */
    private Recipient organizer;
    /**
     * Start of the event.
     */
    private DateTimeTimeZone start;
    /**
     * End of the event.
     */
    private DateTimeTimeZone end;
    /**
     * Is the organizer of the event.
     */
    private Boolean isOrganizer;
    /**
     * IsOrganizer getter.
     * @return If you are the organizer of the event.
     */
    public Boolean getIsOrganizer() {
        return isOrganizer;
    }
    /**
     * IsOrganizer setter.
     * @param isOrganize if you are the organizer of the event.
     */
    public void setIsOrganizer(final Boolean isOrganize) {
        this.isOrganizer = isOrganize;
    }
    /**
     * Id getter.
     * @return id.
     */
    public String getId() {
        return id;
    }
    /**
     * Id setter.
     * @param i id
     */
    public void setId(final String i) {
        this.id = i;
    }
    /**
     * Subject getter.
     * @return subject of the event.
     */
    public String getSubject() {
        return subject;
    }
    /**
     * Subject setter.
     * @param s subject of the event.
     */
    public void setSubject(final String s) {
        this.subject = s;
    }
    /**
     * Organizer getter.
     * @return Organizer of the event.
     */
    public Recipient getOrganizer() {
        return organizer;
    }
    /**
     * Organizer setter.
     * @param r Organizer of the event.
     */
    public void setOrganizer(final Recipient r) {
        this.organizer = r;
    }
    /**
     * Start getter.
     * @return start of the event.
     */
    public DateTimeTimeZone getStart() {
        return start;
    }
    /**
     * Start setter.
     * @param s start of the event.
     */
    public void setStart(final DateTimeTimeZone s) {
        this.start = s;
    }
    /**
     * End getter.
     * @return end of the event.
     */
    public DateTimeTimeZone getEnd() {
        return end;
    }
    /**
     * End setter.
     * @param e end of the event.
     */
    public void setEnd(final DateTimeTimeZone e) {
        this.end = e;
    }
}
