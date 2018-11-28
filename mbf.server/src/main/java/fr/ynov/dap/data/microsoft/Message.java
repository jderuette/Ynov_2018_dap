package fr.ynov.dap.data.microsoft;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    private String id;
    private Date receivedDateTime;
    private Recipient from;
    private Boolean isRead;
    private String subject;
    private String bodyPreview;

    public final String getId() {
        return id;
    }
    public final void setId(String id) {
        this.id = id;
    }
    public final Date getReceivedDateTime() {
        return receivedDateTime;
    }
    public final void setReceivedDateTime(Date receivedDateTime) {
        this.receivedDateTime = receivedDateTime;
    }
    public final Recipient getFrom() {
        return from;
    }
    public final void setFrom(Recipient from) {
        this.from = from;
    }
    public final Boolean getIsRead() {
        return isRead;
    }
    public final void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }
    public final String getSubject() {
        return subject;
    }
    public final void setSubject(String subject) {
        this.subject = subject;
    }
    public final String getBodyPreview() {
        return bodyPreview;
    }
    public final void setBodyPreview(String bodyPreview) {
        this.bodyPreview = bodyPreview;
    }
}