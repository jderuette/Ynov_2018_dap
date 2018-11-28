
package fr.ynov.dap.microsoft;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.ynov.dap.web.microsoft.service.Recipient;


/**
 * Classe Event
 * 
 * @author antod
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event
{
  /**
   * Variable id
   */
  private String id;
  /**
   * Variable subject
   */
  private String subject;
  /**
   * Variable organizer
   */
  private Recipient organizer;
  /**
   * Variable start
   */
  private DateTimeTimeZone start;
  /**
   * Variable end
   */
  private DateTimeTimeZone end;

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
   * @return the organizer
   */
  public Recipient getOrganizer()
  {
    return organizer;
  }

  /**
   * @param organizer the organizer to set
   */
  public void setOrganizer(Recipient organizer)
  {
    this.organizer = organizer;
  }

  /**
   * @return the start
   */
  public DateTimeTimeZone getStart()
  {
    return start;
  }

  /**
   * @param start the start to set
   */
  public void setStart(DateTimeTimeZone start)
  {
    this.start = start;
  }

  /**
   * @return the end
   */
  public DateTimeTimeZone getEnd()
  {
    return end;
  }

  /**
   * @param end the end to set
   */
  public void setEnd(DateTimeTimeZone end)
  {
    this.end = end;
  }

}
