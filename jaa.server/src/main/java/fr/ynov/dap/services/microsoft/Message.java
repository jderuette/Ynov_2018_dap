package fr.ynov.dap.services.microsoft;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/**
 * Message entity used by the Microsoft API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    /**
     * Identifier.
     */
    private String id;
    /**
     * Received date of the Message.
     */
    private Date receivedDateTime;
    /**
     * Sender of the message.
     */
    private Recipient from;
    /**
     * If the message was read.
     */
    private Boolean isRead;
    /**
     * Subject of the message.
     */
    private String subject;
    /**
     * preview of the content of the message.
     */
    private String bodyPreview;

    /**
     * Id getter.
     * @return message id.
     */
    public String getId() {
        return id;
    }
    /**
     * Id setter.
     * @param i message id;
     */
    public void setId(final String i) {
        this.id = i;
    }
    /**
     * ReceivedDateTime getter.
     * @return Received message date time
     */
    public Date getReceivedDateTime() {
        return receivedDateTime;
    }
    /**
     * Received message date time setter.
     * @param dateTime received message date time
     */
    public void setReceivedDateTime(final Date dateTime) {
        this.receivedDateTime = dateTime;
    }
    /**
     * From getter.
     * @return Sender of the message.
     */
    public Recipient getFrom() {
        return from;
    }
    /**
     * From setter.
     * @param f sender of the message.
     */
    public void setFrom(final Recipient f) {
        this.from = f;
    }
    /**
     * IsRead getter.
     * @return If the message was read.
     */
    public Boolean getIsRead() {
        return isRead;
    }
    /**
     * IsRead setter.
     * @param read if the message was read.
     */
    public void setIsRead(final Boolean read) {
        this.isRead = read;
    }
    /**
     * Subject getter.
     * @return Subject of the message.
     */
    public String getSubject() {
        return subject;
    }
    /**
     * Subject setter.
     * @param s subject of the message.
     */
    public void setSubject(final String s) {
        this.subject = s;
    }
    /**
     * BodyPreview getter.
     * @return body preview of the message.
     */
    public String getBodyPreview() {
        return bodyPreview;
    }
    /**
     * BodyPreview setter.
     * @param body bodyPreview of the message.
     */
    public void setBodyPreview(final String body) {
        this.bodyPreview = body;
    }
}
