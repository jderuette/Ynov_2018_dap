package fr.ynov.dap.data.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.ynov.dap.service.microsoft.helper.DateTimeTimeZone;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MicrosoftEvent {
	@JsonProperty("Id")
	private String id;
	@JsonProperty("Subject")
	private String subject;
	@JsonProperty("Organizer")
	private MicrosoftRecipient organizer;
	@JsonProperty("Start")
	private DateTimeTimeZone start;
	@JsonProperty("End")
	private DateTimeTimeZone end;

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

	public MicrosoftRecipient getOrganizer() {
		return organizer;
	}

	public void setOrganizer(MicrosoftRecipient organizer) {
		this.organizer = organizer;
	}

	public DateTimeTimeZone getStart() {
		return start;
	}

	public void setStart(DateTimeTimeZone start) {
		this.start = start;
	}

	public DateTimeTimeZone getEnd() {
		return end;
	}

	public void setEnd(DateTimeTimeZone end) {
		this.end = end;
	}
}
