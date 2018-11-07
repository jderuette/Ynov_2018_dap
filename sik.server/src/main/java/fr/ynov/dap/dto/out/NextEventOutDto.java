package fr.ynov.dap.dto.out;

import java.util.Date;

import fr.ynov.dap.model.CalendarEvent;

/**
 * Model to describe next event of logged user.
 * @author Kévin Sibué
 *
 */
public class NextEventOutDto {

    /**
     * Store event summary.
     */
    private String summary;

    /**
     * Store starting date.
     */
    private Date startingDate;

    /**
     * Store ending date.
     */
    private Date endingDate;

    /**
     * Store status.
     */
    private Integer status;

    /**
     * Store user's status.
     */
    private Integer userStatus;

    /**
     * Default constructors.
     * @param evnt Next event returned by Google's Calendar API
     */
    public NextEventOutDto(final CalendarEvent evnt) {
        this.setSummary(evnt.getSubject());
        this.setStartingDate(evnt.getStartDate());
        this.setEndingDate(evnt.getEndDate());
        this.setStatus(evnt.getStatus().getValue());
        this.setUserStatus(evnt.getCurrentUserStatus().getValue());
    }

    /**
     * @return the eventSummary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * @param val the eventSummary to set
     */
    public void setSummary(final String val) {
        this.summary = val;
    }

    /**
     * @return the startingDate
     */
    public Date getStartingDate() {
        return startingDate;
    }

    /**
     * @param val the startingDate to set
     */
    public void setStartingDate(final Date val) {
        this.startingDate = val;
    }

    /**
     * @return the endingDate
     */
    public Date getEndingDate() {
        return endingDate;
    }

    /**
     * @param val the endingDate to set
     */
    public void setEndingDate(final Date val) {
        this.endingDate = val;
    }

    /**
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param val the status to set
     */
    public void setStatus(final Integer val) {
        this.status = val;
    }

    /**
     * @return the userStatus
     */
    public Integer getUserStatus() {
        return userStatus;
    }

    /**
     * @param val the userStatus to set
     */
    public void setUserStatus(final Integer val) {
        this.userStatus = val;
    }

}
