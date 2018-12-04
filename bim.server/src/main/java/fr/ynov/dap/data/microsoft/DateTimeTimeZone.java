package fr.ynov.dap.data.microsoft;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DateTimeTimeZone implements Comparable<DateTimeTimeZone> {

    /**
     * Default constructor for serialization.
     */
    public DateTimeTimeZone() {
    }

    /**
     * @param dateTime date
     */
    public DateTimeTimeZone(Date dateTime) {
        super();
        this.dateTime = dateTime;
        this.timeZone = "UTC";
    }

    /**
     * dateTime.
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
     * @param dateTime the dateTime to set
     */
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * @return the timeZone
     */
    public String getTimeZone() {
        return timeZone;
    }

    /**
     * @param timeZone the timeZone to set
     */
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    /**
     * Compare datetime
     */
    @Override
    public int compareTo(DateTimeTimeZone o) {
        //TODO bim by Djer |POO| Attention tu néglige le "timeZone", c'est dangereux
        return this.dateTime.compareTo(o.getDateTime());
    }

}