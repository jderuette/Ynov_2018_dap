package fr.ynov.dap.model.outlook;

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

    public String getId() {
        return id;
    }

    public void setId(final String val) {
        this.id = val;
    }

    public Date getReceivedDateTime() {
        return receivedDateTime;
    }

    public void setReceivedDateTime(final Date val) {
        this.receivedDateTime = val;
    }

    public Recipient getFrom() {
        return from;
    }

    public void setFrom(final Recipient val) {
        this.from = val;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(final Boolean val) {
        this.isRead = val;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String val) {
        this.subject = val;
    }

    public String getBodyPreview() {
        return bodyPreview;
    }

	// w
    public void setBodyPreview(final String val) {
        this.bodyPreview = val;
    }
}