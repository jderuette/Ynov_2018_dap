package fr.ynov.dap.data.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {
    private String id;
    private String subject;
    private Recipient organizer;
    private DateTimeTimeZone start;
    private DateTimeTimeZone end;

    public final String getId() {
        return id;
    }
    public final void setId(String identifier) {
        this.id = identifier;
    }
    public final String getSubject() {
        return subject;
    }
    public final void setSubject(String eventSubject) {
        this.subject = eventSubject;
    }
    public final Recipient getOrganizer() {
        return organizer;
    }
    public final void setOrganizer(Recipient eventOrganizer) {
        this.organizer = eventOrganizer;
    }
    public final DateTimeTimeZone getStart() {
        return start;
    }
    public final void setStart(DateTimeTimeZone startDateTime) {
        this.start = startDateTime;
    }
    public final DateTimeTimeZone getEnd() {
        return end;
    }
    public final void setEnd(DateTimeTimeZone endDateTime) {
        this.end = endDateTime;
    }
}