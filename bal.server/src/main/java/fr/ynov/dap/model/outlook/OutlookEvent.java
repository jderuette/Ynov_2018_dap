package fr.ynov.dap.model.outlook;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OutlookEvent {

    private String id;

    private String subject;

    private Recipient organizer;

    private DateTimeZone start;

    private DateTimeZone end;

    private ArrayList<OutlookGuest> guest;

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

    public DateTimeZone getStart() {
        return start;
    }

    public void setStart(final DateTimeZone val) {
        this.start = val;
    }

    public DateTimeZone getEnd() {
        return end;
    }

    public void setEnd(final DateTimeZone val) {
        this.end = val;
    }

    public ArrayList<OutlookGuest> getGuests() {
        return guest;
    }

    public void setAttendees(final ArrayList<OutlookGuest> val) {
        this.guest = val;
    }
}    
