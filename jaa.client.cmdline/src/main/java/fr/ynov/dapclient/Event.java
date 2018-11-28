package fr.ynov.dapclient;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Event object model, inspired from Event of Google Calendar API.
 * @author adrij
 *
 */
public class Event {
    /**
     * Attendees.
     */
    @SerializedName("attendees")
    @Expose
    private List<Attendee> attendees = null;

    /**
     * End infos of Event.
     */
    @SerializedName("end")
    @Expose
    private End end;
    /**
     * Start infos of event.
     */
    @SerializedName("start")
    @Expose
    private Start start;

    /**
     * Status of the event.
     */
    @SerializedName("status")
    @Expose
    private String status;

    /**
     * Summary (subject) of the event.
     */
    @SerializedName("summary")
    @Expose
    private String summary;

    /**
     * attendees getter.
     * @return attendees.
     */
    public List<Attendee> getAttendees() {
        return attendees;
    }

    /**
     * attendees setter.
     * @param a list of attendees of the event.
     */
    public void setAttendees(final List<Attendee> a) {
        this.attendees = a;
    }

    /**
     * end getter.
     * @return End of the event
     */
    public End getEnd() {
        return end;
    }

    /**
     * end setter.
     * @param e end of the event
     */
    public void setEnd(final End e) {
        this.end = e;
    }

    /**
     * start getter.
     * @return start of the event.
     */
    public Start getStart() {
        return start;
    }

    /**
     * start setter.
     * @param s start of the event
     */
    public void setStart(final Start s) {
        this.start = s;
    }

    /**
     * status getter.
     * @return get the status of the event
     */
    public String getStatus() {
        return status;
    }

    /**
     * status setter.
     * @param s status of the event
     */
    public void setStatus(final String s) {
        this.status = s;
    }

    /**
     * summary getter.
     * @return summary of the event
     */
    public String getSummary() {
        return summary;
    }

    /**
     * summary setter.
     * @param s summary of the event
     */
    public void setSummary(final String s) {
        this.summary = s;
    }

}
