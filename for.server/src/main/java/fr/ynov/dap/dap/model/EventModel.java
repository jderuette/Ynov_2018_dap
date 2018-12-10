package fr.ynov.dap.dap.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.ynov.dap.dap.service.DateTimeTimeZone;
import fr.ynov.dap.dap.service.Recipient;


@JsonIgnoreProperties(ignoreUnknown = true)
public class EventModel {

    private String id;
    private String subject;
    private Recipient organizer;
    private DateTimeTimeZone start;
    private DateTimeTimeZone end;

    public String getId() {
        return id;
    }


    public void setId(final String val) {
        this.id = val;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String val) {
        this.subject = val;
    }


    public Recipient getOrganizer() {
        return organizer;
    }

    public void setOrganizer(final Recipient val) {
        this.organizer = val;
    }


    public DateTimeTimeZone getStart() {
        return start;
    }

    public void setStart(final DateTimeTimeZone val) {
        this.start = val;
    }

    public DateTimeTimeZone getEnd() {
        return end;
    }

    public void setEnd(final DateTimeTimeZone val) {
        this.end = val;
    }
}