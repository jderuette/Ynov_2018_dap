package fr.ynov.dapclient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Start used for the Event object.
 * @author adrij
 *
 */
public class Start {
    /**
     * Datetime.
     */
    @SerializedName("dateTime")
    @Expose
    private DateTime dateTime;

    /**
     * dateTime getter.
     * @return dateTime of the event.
     */
    public DateTime getDateTime() {
        return dateTime;
    }

    /**
     * dateTime setter.
     * @param date dateTime of the event.
     */
    public void setDateTime(final DateTime date) {
        this.dateTime = date;
    }
}
