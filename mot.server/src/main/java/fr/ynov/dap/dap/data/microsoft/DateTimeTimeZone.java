package fr.ynov.dap.dap.data.microsoft;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class DateTimeTimeZone.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DateTimeTimeZone {
	private Date dateTime;
	private String timeZone;

	/**
	 * Gets the date time.
	 *
	 * @return the date time
	 */
	public Date getDateTime() {
		return dateTime;
	}

	/**
	 * Sets the date time.
	 *
	 * @param dateTime the new date time
	 */
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	/**
	 * Gets the time zone.
	 *
	 * @return the time zone
	 */
	public String getTimeZone() {
		return timeZone;
	}

	/**
	 * Sets the time zone.
	 *
	 * @param timeZone the new time zone
	 */
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
}
