
package fr.ynov.dap.microsoft;


import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.ynov.dap.web.microsoft.service.Recipient;


/**
 * Classe Message
 * 
 * @author antod
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message
{
  /**
   * Variable id
   */
  private String id;
  /**
   * Variable receivedDateTime
   */
  private Date receivedDateTime;
  /**
   * Variable from
   */
  private Recipient from;
  /**
   * Variable isRead
   */
  private Boolean isRead;
  /**
   * Variable subject
   */
  private String subject;
  /**
   * Variable bodyPreview
   */
  private String bodyPreview;

  /**
   * @return the id
   */
  public String getId()
  {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(String id)
  {
    this.id = id;
  }

  /**
   * @return the receivedDateTime
   */
  public Date getReceivedDateTime()
  {
    return receivedDateTime;
  }

  /**
   * @param receivedDateTime the receivedDateTime to set
   */
  public void setReceivedDateTime(Date receivedDateTime)
  {
    this.receivedDateTime = receivedDateTime;
  }

  /**
   * @return the from
   */
  public Recipient getFrom()
  {
    return from;
  }

  /**
   * @param from the from to set
   */
  public void setFrom(Recipient from)
  {
    this.from = from;
  }

  /**
   * @return the isRead
   */
  public Boolean getIsRead()
  {
    return isRead;
  }

  /**
   * @param isRead the isRead to set
   */
  public void setIsRead(Boolean isRead)
  {
    this.isRead = isRead;
  }

  /**
   * @return the subject
   */
  public String getSubject()
  {
    return subject;
  }

  /**
   * @param subject the subject to set
   */
  public void setSubject(String subject)
  {
    this.subject = subject;
  }

  /**
   * @return the bodyPreview
   */
  public String getBodyPreview()
  {
    return bodyPreview;
  }

  /**
   * @param bodyPreview the bodyPreview to set
   */
  public void setBodyPreview(String bodyPreview)
  {
    this.bodyPreview = bodyPreview;
  }
}
