package fr.ynov.dap.microsoft.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represent a custom object of DateTime from Microsoft Graph API.
 * @author Kévin Sibué
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DateTimeTimeZone {

    /**
     * Current datetime.
     */
    private Date dateTime;

    /**
     * Current timezone.
     */
    private String timeZone;

    /**
     * @return the dateTime
     */
    public Date getDateTime() {
        return dateTime;
    }

    /**
     * @param val the dateTime to set
     */
    public void setDateTime(final Date val) {
        this.dateTime = val;
    }

    /**
     * @return the timeZone
     */
    public String getTimeZone() {
        return timeZone;
    }

    /**
     * @param val the timeZone to set
     */
    public void setTimeZone(final String val) {
        this.timeZone = val;
    }

}
