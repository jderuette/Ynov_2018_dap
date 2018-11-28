package fr.ynov.dap.model.outlook;

import java.util.Date;

import fr.ynov.dap.model.EventAllApi;

/**
 * The Class NextEvent.
 */
public class NextEvent {
	/**
     * Store event summary.
     */
    private String summary;

    /**
     * Store starting date.
     */
    private Date startDate;

    /**
     * Store ending date.
     */
    private Date endDate;

    /**
     * Store user's status.
     */
    private Integer userStatus;

    /**
     * Default constructors.
     * @param evnt Next event returned by Google or Microsoft Calendar API
     */
    public NextEvent(final EventAllApi evnt) {
        this.setSummary(evnt.getSubject());
        this.setStartDate(evnt.getStart());
        this.setEndDate(evnt.getEnd());
        this.setUserStatus(evnt.getCurrentUserStatus().getValue());
    }

    /**
     * Gets the summary.
     *
     * @return the eventSummary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Sets the summary.
     *
     * @param val the eventSummary to set
     */
    public void setSummary(final String val) {
        this.summary = val;
    }

    /**
     * Gets the starting date.
     *
     * @return the startingDate
     */
    public Date getStartingDate() {
        return startDate;
    }

    /**
     * Sets the start date.
     *
     * @param val the startingDate to set
     */
    public void setStartDate(final Date val) {
        this.startDate = val;
    }

    /**
     * Gets the ending date.
     *
     * @return the endingDate
     */
    public Date getEndingDate() {
        return endDate;
    }

    /**
     * Sets the end date.
     *
     * @param val the endingDate to set
     */
    public void setEndDate(final Date val) {
        this.endDate = val;
    }

    /**
     * Gets the user status.
     *
     * @return the userStatus
     */
    public Integer getUserStatus() {
        return userStatus;
    }

    /**
     * Sets the user status.
     *
     * @param val the userStatus to set
     */
    public void setUserStatus(final Integer val) {
        this.userStatus = val;
}
}
