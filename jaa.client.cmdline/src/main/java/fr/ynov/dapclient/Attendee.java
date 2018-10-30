package fr.ynov.dapclient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Attendee used with the Event object.
 * @author adrij
 *
 */
public class Attendee {
	//TODO jaa by Djer attributs devraient être privé, avec acesseurs

    /**
     * user email.
     */
    @SerializedName("email")
    @Expose
    public String email;

    /**
     * Response status (for example 'accepted', 'declined').
     */
    @SerializedName("responseStatus")
    @Expose
    public String responseStatus;

    /**
     * Display name of the user.
     */
    @SerializedName("displayName")
    @Expose
    public String displayName;

    /**
     * If the user is the organizer of the event.
     */
    @SerializedName("organizer")
    @Expose
    public Boolean organizer;

    /**
     * if the user created that event.
     */
    @SerializedName("self")
    @Expose
    public Boolean self;

}
