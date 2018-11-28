package fr.ynov.dap.dap.model;

import java.util.Date;

import fr.ynov.dap.dap.data.microsoft.Recipient;

/**
 * The Class CalendarModel.
 */
public class EventModel {
	
	/** The subject. */
	private String subject;

	/** The date of start. */
	private Date dateOfStart;

	/** The date of end. */
	private Date dateOfEnd;

	/** The recipient. */
	private Recipient recipient;
	
	/**
	 * Instantiates a new calendar model.
	 *
	 * @param subject            the subject
	 * @param dateOfStart            the date of start
	 * @param dateOfEnd            the date of end
	 * @param recipient the recipient
	 */
	public EventModel(String subject, Date dateOfStart, Date dateOfEnd, Recipient recipient) {
		this.subject = subject;
		this.dateOfEnd = dateOfEnd;
		this.dateOfStart = dateOfStart;
		this.recipient = recipient;
	}

	/**
	 * Instantiates a new event model.
	 *
	 * @param noData the no data
	 */
	public EventModel(String noData) {
		this.subject = noData;
	}
	
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
	 * @param subject
	 *            the new subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Gets the date of start.
	 *
	 * @return the date of start
	 */
	public Date getDateOfStart() {
		return dateOfStart;
	}

	/**
	 * Sets the date of start.
	 *
	 * @param dateOfStart
	 *            the new date of start
	 */
	public void setDateOfStart(Date dateOfStart) {
		this.dateOfStart = dateOfStart;
	}

	/**
	 * Gets the date of end.
	 *
	 * @return the date of end
	 */
	public Date getDateOfEnd() {
		return dateOfEnd;
	}

	/**
	 * Sets the date of end.
	 *
	 * @param dateOfEnd
	 *            the new date of end
	 */
	public void setDateOfEnd(Date dateOfEnd) {
		this.dateOfEnd = dateOfEnd;
	}
}
