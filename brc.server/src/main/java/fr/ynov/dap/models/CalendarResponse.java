package fr.ynov.dap.models;

import java.util.Date;

/**
 * The Class CalendarResponse.
 */
public class CalendarResponse {

	private String organizer;
	/** The summary. */
	private String subject;
	
	/** The start. */
	private Date start;
	
	/** The end. */
	private Date end;
	
	/** The status. */
	private String status;
	
	/** The error. */
	private String error;
	
	public String getOrganizer() {
		return organizer;
	}

	public void setOrganizer(String organizer) {
		this.organizer = organizer;
	}

	/**
	 * Gets the summary.
	 *
	 * @return the summary
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Sets the summary.
	 *
	 * @param summary the new summary
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Gets the start.
	 *
	 * @return the start
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * Sets the start.
	 *
	 * @param start the new start
	 */
	public void setStart(Date start) {
		this.start = start;
	}

	/**
	 * Gets the end.
	 *
	 * @return the end
	 */
	public Date getEnd() {
		return end;
	}

	/**
	 * Sets the end.
	 *
	 * @param end the new end
	 */
	public void setEnd(Date end) {
		this.end = end;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * Gets the error.
	 *
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * Sets the error.
	 *
	 * @param error the new error
	 */
	public void setError(String error) {
		this.error = error;
	}
	
	
	/**
	 * Instantiates a new calendar response.
	 */
	public CalendarResponse() {}

	/**
	 * Instantiates a new calendar response.
	 *
	 * @param summary the summary
	 * @param start the start
	 * @param end the end
	 * @param status the status
	 */
	public CalendarResponse(String organizer, String subject, Date start, Date end, String status) {
		this.organizer = organizer;
		this.subject = subject;
		this.start = start;
		this.end = end;
		this.status = status;
		this.error = null;
	}
	
	/**
	 * Instantiates a new calendar response.
	 *
	 * @param error the error
	 */
	public CalendarResponse(String error) {
		this.subject = null;
		this.start = null;
		this.end = null;
		this.status = null;
		this.error = error;
	}
}
