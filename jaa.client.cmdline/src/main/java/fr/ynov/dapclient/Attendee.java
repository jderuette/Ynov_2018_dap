package fr.ynov.dapclient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Attendee used with the Event object.
 * @author adrij
 *
 */
public class Attendee {
    /**
     * user email.
     */
    @SerializedName("email")
    @Expose
    private String email;

    /**
     * Response status (for example 'accepted', 'declined').
     */
    @SerializedName("responseStatus")
    @Expose
    private String responseStatus;

    /**
     * Display name of the user.
     */
    @SerializedName("displayName")
    @Expose
    private String displayName;

    /**
     * If the user is the organizer of the event.
     */
    @SerializedName("organizer")
    @Expose
    private Boolean organizer;

    /**
     * if the user created that event.
     */
    @SerializedName("self")
    @Expose
    private Boolean self;

    /**
     * email getter.
     * @return email of the attendee
     */
    public String getEmail() {
        return email;
    }

    /**
     * email setter.
     * @param e email
     */
    public void setEmail(final String e) {
        this.email = e;
    }

    /**
     * responseStatus getter.
     * @return responseStatus
     */
    public String getResponseStatus() {
        return responseStatus;
    }

    /**
     * responseStatus setter.
     * @param status responseStatus
     */
    public void setResponseStatus(final String status) {
        this.responseStatus = status;
    }

    /**
     * displayName getter.
     * @return displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * displayName setter.
     * @param name displayName
     */
    public void setDisplayName(final String name) {
        this.displayName = name;
    }

    /**
     * organizer getter.
     * @return if this attendee is the organizer of the event
     */
    public Boolean getOrganizer() {
        return organizer;
    }

    /**
     * organizer setter.
     * @param org if this attendee is the organizer of the event
     */
    public void setOrganizer(final Boolean org) {
        this.organizer = org;
    }

    /**
     * self getter.
     * @return if the creator of the event is itself
     */
    public Boolean getSelf() {
        return self;
    }

    /**
     * self setter.
     * @param s if the creator of the event is itself
     */
    public void setSelf(final Boolean s) {
        this.self = s;
    }
}
