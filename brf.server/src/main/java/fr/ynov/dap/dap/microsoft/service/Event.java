package fr.ynov.dap.dap.microsoft.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Florian
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {
    /**.
     * Déclaration de l'id
     */
    private String id;
    /**.
     * Déclaration de subject
     */
    private String subject;
    /**.
     * Déclaration de organizer
     */
    private Recipient organizer;
    /**.
     * Déclaration de start
     */
    private DateTimeTimeZone start;
    /**.
     * Déclaration de end
     */
    private DateTimeTimeZone end;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param theId Modification de la valeur
     */
    public void setId(final String theId) {
        this.id = theId;
    }

    /**
     * @return subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param theSubject Modification de la valeur
     */
    public void setSubject(final String theSubject) {
        this.subject = theSubject;
    }

    /**
     * @return organizer
     */
    public Recipient getOrganizer() {
        return organizer;
    }

    /**
     * @param theOrganizer Modification de la valeur
     */
    public void setOrganizer(final Recipient theOrganizer) {
        this.organizer = theOrganizer;
    }

    /**
     * @return start
     */
    public DateTimeTimeZone getStart() {
        return start;
    }

    /**
     * @param theStart Modification de la valeur
     */
    public void setStart(final DateTimeTimeZone theStart) {
        this.start = theStart;
    }

    /**
     * @return end
     */
    public DateTimeTimeZone getEnd() {
        return end;
    }

    /**
     * @param theEnd Modification de la valeur
     */
    public void setEnd(final DateTimeTimeZone theEnd) {
        this.end = theEnd;
    }
}
