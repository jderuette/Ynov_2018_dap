package fr.ynov.dap.dap.model;

import java.util.Date;

/**
 * The Class CalendarResponse.
 */
public class GoogleCalendarResponse {
	
	/** The start. */
	private Date start;
	
	/** The end. */
	private Date end;
	
	/** The status. */
	private String status;
	
	/** The summary. */
	private String summary;
	
	/**
	 * Instantiates a new calendar response.
	 *
	 * @param start the start
	 * @param end the end
	 * @param status the status
	 * @param summary the summary
	 */
	public GoogleCalendarResponse(Date start, Date end, String status, String summary) {
		this.start = start;
		this.end = end;
		this.status = status; 
		this.summary = summary;
	}

	/**
	 * Sets the summary.
	 *
	 * @param summary the new summary
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * Gets the summary.
	 *
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
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
	 * Gets the end.
	 *
	 * @return the end
	 */
	public Date getEnd() {
		return end;
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
	 * Sets the start.
	 *
	 * @param start the new start
	 */
	public void setStart(Date start) {
		this.start = start;
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
	 * Sets the status.
	 * TODO bot by Djer |JavaDoc| Status de l'evennement ou de l'utilisateur effectuant la requete ? 
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
