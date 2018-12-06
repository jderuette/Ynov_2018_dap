package fr.ynov.dap.dap.google;


/**
 * The Class CalendarResponse.
 */
//TODO bot by Djer |POO| Synonyme de GoogleCalendarResponse ? Cette classe devrait être dans "Model" avec ces copines ? 
public class CalendarResponse {
	
	/** The start. */
	private String start;
	
	/** The end. */
	private String end;
	
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
	public CalendarResponse(String start, String end, String status, String summary) {
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
	public String getStart() {
		return start;
	}

	/**
	 * Gets the end.
	 *
	 * @return the end
	 */
	public String getEnd() {
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
	public void setStart(String start) {
		this.start = start;
	}

	/**
	 * Sets the end.
	 *
	 * @param end the new end
	 */
	public void setEnd(String end) {
		this.end = end;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
