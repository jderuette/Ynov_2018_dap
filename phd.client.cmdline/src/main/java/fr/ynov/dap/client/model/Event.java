package fr.ynov.dap.client.model;

import com.google.api.services.people.v1.model.Date;
/*
 * Event model
 */
public class Event {
	/**
	 * eventBegin is a String variable containing the date of the event begin
	 * eventFinish  is a String variable containing the date of the event finish
	 * eventStatus is a String variable containing the information of the event status
	 * eventUncomming is a String variable containing the name of the event
	 */
	private  String eventBegin;
	private String eventFinish;
	private String eventStatus;
	private String eventUncomming;
	/**
	 * @return the eventBegin
	 */
	public String getEventBegin() {
		return eventBegin;
	}
	/**
	 * @param eventBegin the eventBegin to set
	 */
	public void setEventBegin(String eventBegin) {
		this.eventBegin = eventBegin;
	}
	/**
	 * @return the eventFinish
	 */
	public String getEventFinish() {
		return eventFinish;
	}
	/**
	 * @param eventFinish the eventFinish to set
	 */
	public void setEventFinish(String eventFinish) {
		this.eventFinish = eventFinish;
	}
	/**
	 * @return the eventStatus
	 */
	public String getEventStatus() {
		return eventStatus;
	}
	/**
	 * @param eventStatus the eventStatus to set
	 */
	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}
	/**
	 * @return the eventUncomming
	 */
	public String getEventUncomming() {
		return eventUncomming;
	}
	/**
	 * @param eventUncomming the eventUncomming to set
	 */
	public void setEventUncomming(String eventUncomming) {
		this.eventUncomming = eventUncomming;
	}
	
	
}
