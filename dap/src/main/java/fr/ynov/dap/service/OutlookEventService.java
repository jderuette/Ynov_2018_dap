package fr.ynov.dap.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
/**
 * Permet de récupérer les infos souhaitées lors de l'affichage des événements microsoft.
 * @author abaracas
 *
 */
//TODO baa by Djer |SOA| Cette classe n'est pas un service
public class OutlookEventService {
	@JsonProperty("Id")
	private String id;
	@JsonProperty("Subject")
	private String subject;
	@JsonProperty("Organizer")
	private MicrosoftRecipientService organizer;
	@JsonProperty("Start")
	private DateTimeTimeZoneService start;
	@JsonProperty("End")
	private DateTimeTimeZoneService end;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public MicrosoftRecipientService getOrganizer() {
		return organizer;
	}
	public void setOrganizer(MicrosoftRecipientService organizer) {
		this.organizer = organizer;
	}
	public DateTimeTimeZoneService getStart() {
		return start;
	}
	public void setStart(DateTimeTimeZoneService start) {
		this.start = start;
	}
	public DateTimeTimeZoneService getEnd() {
		return end;
	}
	public void setEnd(DateTimeTimeZoneService end) {
		this.end = end;
	}
}
