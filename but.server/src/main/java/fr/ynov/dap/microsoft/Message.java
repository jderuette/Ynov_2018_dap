package fr.ynov.dap.microsoft;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Entity Message.
 * @author thibault
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    /**
     * Unique Id.
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
     * Is read ?
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
     * @param idToSet the id to set
     */
    public void setId(final String idToSet) {
        this.id = idToSet;
    }
    /**
     * @return the receivedDateTime
     */
    public Date getReceivedDateTime() {
        return receivedDateTime;
    }
    /**
     * @param receivedDateTimeToSet the receivedDateTime to set
     */
    public void setReceivedDateTime(final Date receivedDateTimeToSet) {
        this.receivedDateTime = receivedDateTimeToSet;
    }
    /**
     * @return the from
     */
    public Recipient getFrom() {
        return from;
    }
    /**
     * @param fromToSet the from to set
     */
    public void setFrom(final Recipient fromToSet) {
        this.from = fromToSet;
    }
    /**
     * @return the isRead
     */
    public Boolean getIsRead() {
        return isRead;
    }
    /**
     * @param isReadToSet the isRead to set
     */
    public void setIsRead(final Boolean isReadToSet) {
        this.isRead = isReadToSet;
    }
    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }
    /**
     * @param subjectToSet the subject to set
     */
    public void setSubject(final String subjectToSet) {
        this.subject = subjectToSet;
    }
    /**
     * @return the bodyPreview
     */
    public String getBodyPreview() {
        return bodyPreview;
    }
    /**
     * @param bodyPreviewToSet the bodyPreview to set
     */
    public void setBodyPreview(final String bodyPreviewToSet) {
        this.bodyPreview = bodyPreviewToSet;
    }
}
