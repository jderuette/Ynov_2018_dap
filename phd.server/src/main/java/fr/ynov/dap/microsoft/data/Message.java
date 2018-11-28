package fr.ynov.dap.microsoft.data;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author Dom
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    /**
     *
     */
    private String id;
    /**
     *
     */
    private Date receivedDateTime;
    /**
     *
     */
    private Recipient from;
    /**
     *
     */
    private Boolean isRead;
    /**
     *
     */
    private String subject;
    /**
     *
     */
    private String bodyPreview;

    /**
     *
     * @return .
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param mId .
     */
    public void setId(final String mId) {
        this.id = mId;
    }

    /**
     *
     * @return .
     */
    public Date getReceivedDateTime() {
        return receivedDateTime;
    }

    /**
     *
     * @param mReceivedDateTime .
     */
    public void setReceivedDateTime(final Date mReceivedDateTime) {
        this.receivedDateTime = mReceivedDateTime;
    }

    /**
     *
     * @return .
     */
    public Recipient getFrom() {
        return from;
    }

    /**
     *
     * @param mFrom .
     */
    public void setFrom(final Recipient mFrom) {
        this.from = mFrom;
    }

    /**
     *
     * @return .
     */
    public Boolean getIsRead() {
        return isRead;
    }

    /**
     *
     * @param mIsRead .
     */
    public void setIsRead(final Boolean mIsRead) {
        this.isRead = mIsRead;
    }

    /**
     *
     * @return .
     */
    public String getSubject() {
        return subject;
    }

    /**
     *
     * @param mSubject .
     */
    public void setSubject(final String mSubject) {
        this.subject = mSubject;
    }

    /**
     *
     * @return .
     */
    public String getBodyPreview() {
        return bodyPreview;
    }

    /**
     *
     * @param mBodyPreview .
     */
    public void setBodyPreview(final String mBodyPreview) {
        this.bodyPreview = mBodyPreview;
    }
}
