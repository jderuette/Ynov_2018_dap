package com.ynov.dap.model.microsoft;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class Message.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

    /** The id. */
    private String id;

    /** The received date time. */
    private Date receivedDateTime;

    /** The from. */
    private Recipient from;

    /** The is read. */
    private Boolean isRead;

    /** The subject. */
    private String subject;

    /** The body preview. */
    private String bodyPreview;

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * Gets the received date time.
     *
     * @return the received date time
     */
    public Date getReceivedDateTime() {
        return receivedDateTime;
    }

    /**
     * Sets the received date time.
     *
     * @param receivedDateTime the new received date time
     */
    public void setReceivedDateTime(final Date receivedDateTime) {
        this.receivedDateTime = receivedDateTime;
    }

    /**
     * Gets the from.
     *
     * @return the from
     */
    public Recipient getFrom() {
        return from;
    }

    /**
     * Sets the from.
     *
     * @param from the new from
     */
    public void setFrom(final Recipient from) {
        this.from = from;
    }

    /**
     * Gets the checks if is read.
     *
     * @return the checks if is read
     */
    public Boolean getIsRead() {
        return isRead;
    }

    /**
     * Sets the checks if is read.
     *
     * @param isRead the new checks if is read
     */
    public void setIsRead(final Boolean isRead) {
        this.isRead = isRead;
    }

    /**
     * Gets the subject.
     *
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the subject.
     *
     * @param subject the new subject
     */
    public void setSubject(final String subject) {
        this.subject = subject;
    }

    /**
     * Gets the body preview.
     *
     * @return the body preview
     */
    public String getBodyPreview() {
        return bodyPreview;
    }

    /**
     * Sets the body preview.
     *
     * @param bodyPreview the new body preview
     */
    public void setBodyPreview(final String bodyPreview) {
        this.bodyPreview = bodyPreview;
    }
}