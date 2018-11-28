package fr.ynov.dap.data.microsoft;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DateTimeTimeZone {
    private Date dateTime;
    private String timeZone;

    public final Date getDateTime() {
        return dateTime;
    }
    public final void setDateTime(Date date) {
        this.dateTime = date;
    }
    public final String getTimeZone() {
        return timeZone;
    }
    public final void setTimeZone(String time) {
        this.timeZone = time;
    }
}