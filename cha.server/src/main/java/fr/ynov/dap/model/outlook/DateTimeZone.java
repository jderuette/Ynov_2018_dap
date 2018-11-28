package fr.ynov.dap.model.outlook;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class DateTimeZone.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DateTimeZone {

    /**
     * Current datetime.
     */
    private Date dateTime;

    /**
     * Current timezone.
     */
    private String timeZone;

    /**
     * Gets the date time.
     *
     * @return the dateTime
     */
    public Date getDateTime() {
        return dateTime;
    }

    /**
     * Sets the date time.
     *
     * @param val the dateTime to set
     */
    public void setDateTime(final Date val) {
        this.dateTime = val;
    }

    /**
     * Gets the time zone.
     *
     * @return the timeZone
     */
    public String getTimeZone() {
        return timeZone;
    }

    /**
     * Sets the time zone.
     *
     * @param val the timeZone to set
     */
    public void setTimeZone(final String val) {
        this.timeZone = val;
    }

}