package fr.ynov.dapclient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Used by the Event object.
 * @author adrij
 *
 */
public class End {

    /**
     * Datetime.
     */
    @SerializedName("dateTime")
    @Expose
    private DateTime dateTime;

    /**
     * dateTime getter.
     * @return dateTime
     */
    public DateTime getDateTime() {
        return dateTime;
    }

    /**
     * dateTime setter.
     * @param date dateTime
     */
    public void setDateTime(final DateTime date) {
        this.dateTime = date;
    }
}
