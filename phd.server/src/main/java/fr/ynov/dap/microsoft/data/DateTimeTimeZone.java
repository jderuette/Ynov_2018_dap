package fr.ynov.dap.microsoft.data;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author Dom .
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DateTimeTimeZone {
    /**
     *
     */
    private Date dateTime;
    /**
     *
     */
    private String timeZone;

    /**
     *
     * @return .
     */
    public Date getDateTime() {
        return dateTime;
    }

    /**
     *
     * @param mDateTime .
     */
    public void setDateTime(final Date mDateTime) {
        this.dateTime = mDateTime;
    }

    /**
     *
     * @return .
     */
    public String getTimeZone() {
        return timeZone;
    }

    /**
     *
     * @param mTimeZone .
     */
    public void setTimeZone(final String mTimeZone) {
        this.timeZone = mTimeZone;
    }
}
