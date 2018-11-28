package fr.ynov.dap.models.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

/**
 * MicrosoftMessage
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MicrosoftMessage {

    /**
     * id
     */
    private String id;

    /**
     * receivedDateTime
     */
    private Date receivedDateTime;

    /**
     * from
     */
    private MicrosoftRecipient from;

    /**
     * isRead
     */
    private Boolean isRead;

    /**
     * subject
     */
    private String subject;

    /**
     * bodyPreview
     */
    private String bodyPreview;


    /*
    GETTERS AND SETTERS
     */

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
