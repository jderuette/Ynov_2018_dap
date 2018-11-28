package fr.ynov.dapclient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * DateTime class used by Event.
 * @author adrij
 *
 */
public class DateTime {
    /**
     * TimeStamp Value.
     */
    @SerializedName("value")
    @Expose
    private Long value;

    /**
     * Is Date only.
     */
    @SerializedName("dateOnly")
    @Expose
    private Boolean dateOnly;


    /**
     * Time zone shift.
     */
    @SerializedName("timeZoneShift")
    @Expose
    private Integer timeZoneShift;

    /**
     * value getter.
     * @return value
     */
    public Long getValue() {
        return value;
    }

    /**
     * value setter.
     * @param v date time value
     */
    public void setValue(final Long v) {
        this.value = v;
    }

    /**
     * dateOnly getter.
     * @return if it only get Date
     */
    public Boolean getDateOnly() {
        return dateOnly;
    }

    /**
     * dateOnly setter.
     * @param date if it only get date
     */
    public void setDateOnly(final Boolean date) {
        this.dateOnly = date;
    }

    /**
     * timeZoneShift getter.
     * @return timeZone
     */
    public Integer getTimeZoneShift() {
        return timeZoneShift;
    }

    /**
     * timeZone setter.
     * @param timeZone timeZoneShift
     */
    public void setTimeZoneShift(final Integer timeZone) {
        this.timeZoneShift = timeZone;
    }
}
