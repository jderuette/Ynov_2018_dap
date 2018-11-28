package fr.ynov.dap.microsoft.data;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Mon_PC
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DateTimeTimeZone {
    /**.
     * propriété dateTime
     */
    private Date dateTime;
    /**.
     * propriété timeZone
     */
    private String timeZone;

    /**
     * @return dateTime
     */
    public Date getDateTime() {
        return dateTime;
    }

    /**.
     * @param newDateTime set
     */
    public void setDateTime(final Date newDateTime) {
        this.dateTime = newDateTime;
    }

    /**
     * @return timeZone
     */
    public String getTimeZone() {
        return timeZone;
    }

    /**.
     * @param newTimeZone set
     */
    public void setTimeZone(final String newTimeZone) {
        this.timeZone = newTimeZone;
    }
}
