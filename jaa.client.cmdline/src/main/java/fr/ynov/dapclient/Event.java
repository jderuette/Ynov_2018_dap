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
	//TODO jaa by Djer attributs devraient être privé, avec acesseurs
    /**
     * Attendees.
     */
    @SerializedName("attendees")
    @Expose
    public List<Attendee> attendees = null;

    /**
     * End infos of Event.
     */
    @SerializedName("end")
    @Expose
    public End end;

    /**
     * Start infos of event.
     */
    @SerializedName("start")
    @Expose
    public Start start;

    /**
     * Status of the event.
     */
    @SerializedName("status")
    @Expose
    public String status;

    /**
     * Summary (subject) of the event.
     */
    @SerializedName("summary")
    @Expose
    public String summary;

}
