package fr.ynov.dap.dap.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MicrosoftMail {

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
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(final String id) {
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
    public void setReceivedDateTime(final Date receivedDateTime) {
        this.receivedDateTime = receivedDateTime;
    }

    /**
     * @return the from
     */
    public Recipient getFrom() {
        return from;
    }

    /**
     * @param from the from to set
     */
    public void setFrom(final Recipient from) {
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
    public void setIsRead(final Boolean isRead) {
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
    public void setSubject(final String subject) {
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
    public void setBodyPreview(final String bodyPreview) {
        this.bodyPreview = bodyPreview;
    }

}
