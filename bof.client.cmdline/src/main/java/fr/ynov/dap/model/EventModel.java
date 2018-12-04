package fr.ynov.dap.model;

import java.util.Date;

public class EventModel extends MasterModel {
    
    //TODO bof by Djer |POO| évite de mélanger les attributs et les getter/setter (ordre : Constantes, attributs statics, attributs, initializateur static, constructeurs, méthodes métiers, méthpode "générique" (toString, equals, hasCode,...), getter/setter)

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
	
	public EventModel() {
		
	}
	
	@Override
	public String toString() {
		return "Votre prochain événement: \n Titre : " + summary + " \n Date de début: " + startDate + "\n Date de fin " + endDate;
	}

}
