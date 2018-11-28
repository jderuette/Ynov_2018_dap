package fr.ynov.dap.dap.model;

import java.util.Date;

/**
 *
 * @author David_tepoche
 *
 */
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
     * @param dateTm the dateTime to set
     */
    public void setDateTime(final Date dateTm) {
        this.dateTime = dateTm;
    }

    /**
     * @return the timeZone
     */
    public String getTimeZone() {
        return timeZone;
    }

    /**
     * @param timeZn the timeZone to set
     */
    public void setTimeZone(final String timeZn) {
        this.timeZone = timeZn;
    }

}
