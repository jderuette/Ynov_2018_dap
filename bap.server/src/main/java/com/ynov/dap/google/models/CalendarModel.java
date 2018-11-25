package com.ynov.dap.google.models;

import java.util.Date;

/**
 * Model Calendar.
 * @author POL
 */
public class CalendarModel {

    /**
     * Gets the subject.
     *
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the subject.
     *
     * @param inSubject the new subject
     */
    public void setSubject(final String inSubject) {
        this.subject = inSubject;
    }

    /**
     * Gets the start date.
     *
     * @return the start date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date.
     *
     * @param inStartDate the new start date
     */
    public void setStartDate(final Date inStartDate) {
        this.startDate = inStartDate;
    }

    /**
     * Gets the end date.
     *
     * @return the end date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date.
     *
     * @param inEndDate the new end date
     */
    public void setEndDate(final Date inEndDate) {
        this.endDate = inEndDate;
    }

    /**
     * Gets the state.
     *
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the state.
     *
     * @param inState the new state
     */
    public void setState(final String inState) {
        this.state = inState;
    }

    /** The subject. */
    private String subject;

    /** The start date. */
    private Date startDate;

    /** The end date. */
    private Date endDate;

    /** The state. */
    private String state;

    /**
     * Instantiates a new calendar model.
     *
     * @param inSubject *calendar subject*
     * @param inStartDate *calendar start date*
     * @param inEndDate *calendar end date*
     * @param inState *calendar state*
     */
    public CalendarModel(final String inSubject, final Date inStartDate,
             final Date inEndDate, final String inState) {
        this.subject = inSubject;
        this.startDate = inStartDate;
        this.endDate = inEndDate;
        this.state = inState;
    }

	public CalendarModel() {
		// TODO Auto-generated constructor stub
	}
}
