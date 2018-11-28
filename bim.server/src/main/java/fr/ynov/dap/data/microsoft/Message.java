package fr.ynov.dap.data.microsoft;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Outlook message.
 * @author MBILLEMAZ
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    /**
     * id.
     */
  private String id;
  /**
   * received dateTime.
   */
  private Date receivedDateTime;
  /**
   * from.
   */
  private Recipient from;
  /**
   * isRead.
   */
  private Boolean isRead;
  /**
   * subject.
   */
  private String subject;
  /**
   * body preview.
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
public void setId(String id) {
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
public void setReceivedDateTime(Date receivedDateTime) {
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
public void setFrom(Recipient from) {
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
public void setIsRead(Boolean isRead) {
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
public void setSubject(String subject) {
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
public void setBodyPreview(String bodyPreview) {
    this.bodyPreview = bodyPreview;
}

  
}