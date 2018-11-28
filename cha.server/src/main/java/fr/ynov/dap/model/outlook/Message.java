package fr.ynov.dap.model.outlook;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

    /**
     * Id.
     */
    private String id;

    /**
     * Received date time.
     */
    private Date receivedDateTime;

    /**
     * From.
     */
    private Recipient from;

    /**
     * Is read.
     */
    private Boolean isRead;

    /**
     * Subject.
     */
    private String subject;

    /**
     * Body preview.
     */
    private String bodyPreview;

    /**
     * Return id.
     * @return Id
     */
    public String getId() {
        return id;
    }

    /**
     * set id.
     * @param val id value
     */
    public void setId(final String val) {
        this.id = val;
    }

    /**
     * Received date time.
     * @return Received date
     */
    public Date getReceivedDateTime() {
        return receivedDateTime;
    }

    /**
     * Set Received date.
     * @param val Received date
     */
    public void setReceivedDateTime(final Date val) {
        this.receivedDateTime = val;
    }

    /**
     * Get recipient.
     * @return Recipient
     */
    public Recipient getFrom() {
        return from;
    }

    /**
     * From value.
     * @param val from
     */
    public void setFrom(final Recipient val) {
        this.from = val;
    }

    /**
     * Get is read.
     * @return state
     */
    public Boolean getIsRead() {
        return isRead;
    }

    /**
     * Is read.
     * @param val state
     */
    public void setIsRead(final Boolean val) {
        this.isRead = val;
    }

    /**
     * Subject.
     * @return subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * set subject.
     * @param val subject
     */
    public void setSubject(final String val) {
        this.subject = val;
    }

    /**
     * Return body preview.
     * @return Body
     */
    public String getBodyPreview() {
        return bodyPreview;
    }

    /**
     * Set body preview.
     * @param val body
     */
    public void setBodyPreview(final String val) {
        this.bodyPreview = val;
    }
}