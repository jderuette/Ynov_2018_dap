package fr.ynov.dap.model;

import java.util.Date;

public class EventModel {
	private Date start;
	
	private Date end;
	
	private String status;
	
	private String summary;
	
	public EventModel(Date start, Date end, String status, String summary) {
		this.start = start;
		this.end = end;
		this.status = status; 
		this.summary = summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getSummary() {
		return summary;
	}

	public Date getStart() {
		return start;
	}

	public Date getEnd() {
		return end;
	}

	public String getStatus() {
		return status;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
