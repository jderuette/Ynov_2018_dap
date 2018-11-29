package fr.ynov.dap.service;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
/**
 * Permet de gérer les dates au mieux.
 * @author abaracas
 *
 */
public class DateTimeTimeZoneService {
	@JsonProperty("DateTime")
	private Date dateTime;
	@JsonProperty("TimeZone")
	private String timeZone;
	/**
	 * @return the dateTime
	 */
	public Date getDateTime() {
	    return dateTime;
	}
	/**
	 * @param dateTime the dateTime to set
	 */
	public void setDateTime(Date dateTime) {
	    this.dateTime = dateTime;
	}
	/**
	 * @return the timeZone
	 */
	public String getTimeZone() {
	    return timeZone;
	}
	/**
	 * @param timeZone the timeZone to set
	 */
	public void setTimeZone(String timeZone) {
	    this.timeZone = timeZone;
	}
	
	
}
