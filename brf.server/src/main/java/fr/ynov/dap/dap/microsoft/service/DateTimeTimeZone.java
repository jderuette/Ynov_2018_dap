package fr.ynov.dap.dap.microsoft.service;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Florian
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DateTimeTimeZone {
    /**.
     * Déclaration de dateTime
     */
    private Date dateTime;
    /**.
     * Déclaration de timeZone
     */
    private String timeZone;

    /**
     * @return dateTime
     */
    public Date getDateTime() {
        return dateTime;
    }

    /**
     * @param theDateTime Modification de la valeur
     */
    public void setDateTime(final Date theDateTime) {
        this.dateTime = theDateTime;
    }

    /**
     * @return timeZone
     */
    public String getTimeZone() {
        return timeZone;
    }

    /**
     * @param theTimeZone Modification de la valeur
     */
    public void setTimeZone(final String theTimeZone) {
        this.timeZone = theTimeZone;
    }
}
