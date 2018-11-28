
package fr.ynov.dap.microsoft;


import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * Classe DateTimeTimeZone
 * 
 * @author antod
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DateTimeTimeZone
{
  /**
   * Variable dateTime
   */
  private Date dateTime;

  /**
   * Variable timeZone
   */
  private String timeZone;

  /**
   * Récupère dateTime
   * 
   * @return
   */
  public Date getDateTime()
  {
    return dateTime;
  }

  /**
   * Assigne le dateTime
   * 
   * @param dateTime
   */
  public void setDateTime(Date dateTime)
  {
    this.dateTime = dateTime;
  }

  /**
   * timeZone
   * 
   * @return
   */
  public String getTimeZone()
  {
    return timeZone;
  }

  /**
   * timeZone
   * 
   * @param timeZone
   */
  public void setTimeZone(String timeZone)
  {
    this.timeZone = timeZone;
  }
}
