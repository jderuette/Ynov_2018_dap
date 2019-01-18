package fr.ynov.dap.metier;

import java.util.Date;

/**
 * @author acer
 *
 */

public class Evenement {
    private String organizer;
    private String subject;
    private Date startDate;
    private Date endDate;

    /**
     * 
     */
    public Evenement() {
        super();

    }

    /**
     * @param organizer
     * @param subject
     * @param startDate
     * @param endDate
     */
    public Evenement(final String organizer, final String subject, final Date startDate, final Date endDate) {
        super();
        this.organizer = organizer;
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * @return the organizer
     */
    public String getOrganizer() {
        return organizer;
    }

    /**
     * @param newOrganizer the organizer to set
     */
    public void setOrganizer(final String newOrganizer) {
        this.organizer = newOrganizer;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param newSubject the subject to set
     */
    public void setSubject(final String newSubject) {
        this.subject = newSubject;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param newStartDate the startDate to set
     */
    public void setStartDate(final Date newStartDate) {
        this.startDate = newStartDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param newEndDate the endDate to set
     */
    public void setEndDate(final Date newEndDate) {
        this.endDate = newEndDate;
    }
}
