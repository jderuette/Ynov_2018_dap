package fr.ynov.dap.dap.model;

import java.util.Date;

public class EventModel extends MasterModel {

	private String summary;
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	private Date startDate;
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	private Date endDate;
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public EventModel(String summary, Date startDate, Date endDate) {
		super();
		this.summary = summary;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	

}
