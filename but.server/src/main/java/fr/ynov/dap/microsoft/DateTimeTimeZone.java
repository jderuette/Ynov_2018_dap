package fr.ynov.dap.microsoft;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Entity DateTimeTimeZone.
 * @author thibault
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DateTimeTimeZone {
    /**
     * Date time.
     */
    private Date dateTime;
    /**
     * Timezone.
     */
    private String timeZone;
    /**
     * @return the dateTime
     */
    public Date getDateTime() {
        return dateTime;
    }
    /**
     * @param dateTimeToSet the dateTime to set
     */
    public void setDateTime(final Date dateTimeToSet) {
        this.dateTime = dateTimeToSet;
    }
    /**
     * @return the timeZone
     */
    public String getTimeZone() {
        return timeZone;
    }
    /**
     * @param timeZoneToSet the timeZone to set
     */
    public void setTimeZone(final String timeZoneToSet) {
        this.timeZone = timeZoneToSet;
    }
}
