package fr.ynov.dap.dap.microsoft.service;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Florian
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    /**.
     * Déclaration de l'id
     */
    private String id;
    /**.
     * Déclaration de receivedDateTime
     */
    private Date receivedDateTime;
    /**.
     * Déclaration de from
     */
    private Recipient from;
    /**.
     * Déclaration de isRead
     */
    private Boolean isRead;
    /**.
     * Déclaration de subject
     */
    private String subject;
    /**.
     * Déclaration de bodyPreview
     */
    private String bodyPreview;

    /**
     * @return l'id
     */
    public String getId() {
        return id;
    }

    /**
     * @param theId changement de la valeur
     */
    public void setId(final String theId) {
        this.id = theId;
    }

    /**
     * @return receivedDateTime
     */
    public Date getReceivedDateTime() {
        return receivedDateTime;
    }

    /**
     * @param theReceivedDateTime changement de la valeur
     */
    public void setReceivedDateTime(final Date theReceivedDateTime) {
        this.receivedDateTime = theReceivedDateTime;
    }

    /**
     * @return from
     */
    public Recipient getFrom() {
        return from;
    }

    /**
     * @param theFrom changement de la valeur
     */
    public void setFrom(final Recipient theFrom) {
        this.from = theFrom;
    }

    /**
     * @return isRead
     */
    public Boolean getIsRead() {
        return isRead;
    }

    /**
     * @param theIsRead changement de la valeur
     */
    public void setIsRead(final Boolean theIsRead) {
        this.isRead = theIsRead;
    }

    /**
     * @return subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param theSubject changement de la valeur
     */
    public void setSubject(final String theSubject) {
        this.subject = theSubject;
    }

    /**
     * @return bodyPreview
     */
    public String getBodyPreview() {
        return bodyPreview;
    }

    /**
     * @param theBodyPreview changement de la valeur
     */
    public void setBodyPreview(final String theBodyPreview) {
        this.bodyPreview = theBodyPreview;
    }
}
