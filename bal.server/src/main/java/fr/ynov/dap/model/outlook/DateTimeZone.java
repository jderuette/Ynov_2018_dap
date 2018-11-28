package fr.ynov.dap.model.outlook;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class DateTimeZone.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DateTimeZone {

    /** The date time. */
    private Date dateTime;

    /** The time zone. */
    private String timeZone;

    /**
     * Gets the date time.
     *
     * @return the date time
     */
    public Date getDateTime() {
        return dateTime;
    }

    /**
     * Sets the date time.
     *
     * @param val the new date time
     */
    public void setDateTime(final Date val) {
        this.dateTime = val;
    }

    /**
     * Gets the time zone.
     *
     * @return the time zone
     */
    public String getTimeZone() {
        return timeZone;
    }

    /**
     * Sets the time zone.
     *
     * @param val the new time zone
     */
    public void setTimeZone(final String val) {
        this.timeZone = val;
    }

}