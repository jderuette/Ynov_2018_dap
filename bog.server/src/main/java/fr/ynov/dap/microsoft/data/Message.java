package fr.ynov.dap.microsoft.data;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Mon_PC
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    /**.
     * Propriété id
     */
    private String id;
    /**.
     * Propriété receivedDateTime
     */
    private Date receivedDateTime;
    /**.
     * Propriété from
     */
    private Recipient from;
    /**.
     * Propriété isRead
     */
    private Boolean isRead;
    /**.
     * Propriété subject
     */
    private String subject;
    /**.
     * Propriété bodyPreview
     */
    private String bodyPreview;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**.
     * Set new Id
     * @param newId correspondant au nouveau id
     */
    public void setId(final String newId) {
        this.id = newId;
    }

    /**
     * @return receivedDateTime
     */
    public Date getReceivedDateTime() {
        return receivedDateTime;
    }

    /**.
     * Set new receivedDateTime
     * @param newReceivedDateTime correspondant au nouveau receivedDateTime
     */
    public void setReceivedDateTime(final Date newReceivedDateTime) {
        this.receivedDateTime = newReceivedDateTime;
    }

    /**
     * @return from
     */
    public Recipient getFrom() {
        return from;
    }

    /**.
     * Set new from
     * @param newFrom correspondant au nouveau from
     */
    public void setFrom(final Recipient newFrom) {
        this.from = newFrom;
    }

    /**
     * @return isRead
     */
    public Boolean getIsRead() {
        return isRead;
    }

    /**.
     * set new isRead
     * @param newIsRead correspondant au nouveau état isRead
     */
    public void setIsRead(final Boolean newIsRead) {
        this.isRead = newIsRead;
    }

    /**
     * @return subject
     */
    public String getSubject() {
        return subject;
    }

    /**.
     * Set new subject
     * @param newSubject correspondant au nouveau subject
     */
    public void setSubject(final String newSubject) {
        this.subject = newSubject;
    }

    /**
     * @return bodyPreview
     */
    public String getBodyPreview() {
        return bodyPreview;
    }

    /**.
     * Set new bodyPreview
     * @param newBodyPreview correspondant au nouveau bodyPreview
     */
    public void setBodyPreview(final String newBodyPreview) {
        this.bodyPreview = newBodyPreview;
    }
}
