package fr.ynov.dap.service;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Permet de
 * @author abaracas
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
/**
 * Indique les différents champs relatif aux mails microsoft.
 * @author abaracas
 *
 */
//TODO baa by Djer |SOA| Cette classe n'est pas un service
public class OutlookMessageService {
	@JsonProperty("Id")
	private String id;
	@JsonProperty("ReceivedDateTime")
	private Date receivedDateTime;
	@JsonProperty("From")
	private MicrosoftRecipientService from;
	@JsonProperty("IsRead")
	private Boolean isRead;
	@JsonProperty("Subject")
	private String subject;
	@JsonProperty("BodyPreview")
	private String bodyPreview;
	/**
	 * @return the id
	 */
	public String getId() {
	    return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
	    this.id = id;
	}
	/**
	 * @return the receivedDateTime
	 */
	public Date getReceivedDateTime() {
	    return receivedDateTime;
	}
	/**
	 * @param receivedDateTime the receivedDateTime to set
	 */
	public void setReceivedDateTime(Date receivedDateTime) {
	    this.receivedDateTime = receivedDateTime;
	}
	/**
	 * @return the from
	 */
	public MicrosoftRecipientService getFrom() {
	    return from;
	}
	/**
	 * @param from the from to set
	 */
	public void setFrom(MicrosoftRecipientService from) {
	    this.from = from;
	}
	/**
	 * @return the isRead
	 */
	public Boolean getIsRead() {
	    return isRead;
	}
	/**
	 * @param isRead the isRead to set
	 */
	public void setIsRead(Boolean isRead) {
	    this.isRead = isRead;
	}
	/**
	 * @return the subject
	 */
	public String getSubject() {
	    return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
	    this.subject = subject;
	}
	/**
	 * @return the bodyPreview
	 */
	public String getBodyPreview() {
	    return bodyPreview;
	}
	/**
	 * @param bodyPreview the bodyPreview to set
	 */
	public void setBodyPreview(String bodyPreview) {
	    this.bodyPreview = bodyPreview;
	}
	
	
}
