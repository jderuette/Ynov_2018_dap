package fr.ynov.dap.data.microsoft;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MicrosoftMessage {
	@JsonProperty("Id")
	private String id;
	@JsonProperty("ReceivedDateTime")
	private Date receivedDateTime;
	@JsonProperty("From")
	private MicrosoftRecipient from;
	@JsonProperty("IsRead")
	private Boolean isRead;
	@JsonProperty("Subject")
	private String subject;
	@JsonProperty("BodyPreview")
	private String bodyPreview;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getReceivedDateTime() {
		return receivedDateTime;
	}
	public void setReceivedDateTime(Date receivedDateTime) {
		this.receivedDateTime = receivedDateTime;
	}
	public MicrosoftRecipient getFrom() {
		return from;
	}
	public void setFrom(MicrosoftRecipient from) {
		this.from = from;
	}
	public Boolean getIsRead() {
		return isRead;
	}
	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBodyPreview() {
		return bodyPreview;
	}
	public void setBodyPreview(String bodyPreview) {
		this.bodyPreview = bodyPreview;
	}
}
