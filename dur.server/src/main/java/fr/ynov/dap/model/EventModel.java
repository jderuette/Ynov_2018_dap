package fr.ynov.dap.model;

import java.util.Date;

/**
 * The Class EventModel.
 * TODO dur by Djer |POO| Comme tu n'utilises pas cette classe dans ton code, tu peux la supprimer
 */
public class EventModel {

	/** The title. */
	private String title;
	
	/** The status. */
	private String status;
	
	/** The start date. */
	private Date startDate;
	
	/** The end date. */
	private Date endDate;
	
	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
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
	 * @param startDate the new start date
	 */
	public void setStartDate(long startDate) {
		this.startDate = new Date(startDate);
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
	 * @param endDate the new end date
	 */
	public void setEndDate(long endDate) {
		this.endDate = new Date(endDate);
	}

	
}
