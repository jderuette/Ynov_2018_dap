package fr.ynov.dap.dap.model;

import java.io.Serializable;
import java.util.Date;

import com.google.api.services.calendar.model.EventDateTime;

//TODO for by Djer Si tu implemente serializable, il te FAUT un serialVersionUID !
public class CalendarModel implements Serializable{

	private String eventName;
	private EventDateTime eventDate;
	private EventDateTime eventEndDate;
	private String eventStatus;
	
	/**
	 * Constructeur du CalendarModel
	 * @param summary
	 * @param start
	 * @param end
	 * @param status
	 */
	public CalendarModel(String summary, EventDateTime start, EventDateTime end, String status) {
		this.eventName = summary;
		this.eventDate = start;
		this.eventEndDate = end;
		this.eventStatus = status;
	}
	/**
	 * Retourne le nom de l'évennement
	 * @return 
	 */
	private String GetEventName()
	{
		return this.eventName;
	}
	/**
	 * Défini le nom de l'évennement
	 * @param value
	 */
	private void SetEventName(String value)
	{
		this.eventName = value;
	}
	/**
	 * Retourne la date de début de l'évennement
	 * @return
	 */
	private EventDateTime GetEventDate()
	{
		return this.eventDate;
	}
	/**
	 * Défini la date de début de l'évennement
	 * @param value
	 */
	private void SetEventDate(EventDateTime value)
	{
		this.eventDate = value;
	}
	/**
	 * Retourne la date de fin de l'évennement
	 * @return
	 */
	private EventDateTime GetEventEndDate()
	{
		return this.eventDate;
	}
	/**
	 * Défini la date de fin de l'évennement
	 * @param value
	 */
	private void SetEventEndDate(EventDateTime value)
	{
		this.eventEndDate = value;
	}
	/**
	 * Retourne le status de l'évennement
	 * @return
	 */
	private String GetEventstatus()
	{
		return this.eventStatus;
	}
	/**
	 * Défini le status de l'évennement
	 * @param value
	 */
	private void SetEventStatus(String value)
	{
		this.eventStatus = value;
	}
	
	@Override
	/**
	 * Retourne une String des infos de l'évennement
	 */
    public String toString() {
        return "Event [Title=" + this.eventName + ", StartDate=" + this.eventDate + ", EndDate=" + this.eventEndDate + ", EventStatus=" + this.eventStatus
                + "]";
    }
}
