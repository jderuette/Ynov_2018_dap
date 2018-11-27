package fr.ynov.dap.services.microsoft;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * DateTimeTimeZone entity used by the Microsoft API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DateTimeTimeZone {
    /**
     * Date time.
     */
    private Date dateTime;
    /**
     * Time zone.
     */
    private String timeZone;

    /**
     * DateTime getter.
     * @return date time.
     */
    public Date getDateTime() {
        return dateTime;
    }

    /**
     * DateTime setter.
     * @param date date time.
     */
    public void setDateTime(final Date date) {
        this.dateTime = date;
    }

    /**
     * TimeZone getter.
     * @return time zone.
     */
    public String getTimeZone() {
        return timeZone;
    }

    /**
     * TimeZone setter.
     * @param zone time zone.
     */
    public void setTimeZone(final String zone) {
        this.timeZone = zone;
    }
}
