
package fr.ynov.dap.web;


/**
 * Classe Upcoming Event pour récupérer les évènements à venir de microsoft
 * 
 * @author antod
 *
 */
public class UpcomingEvent
{
  /**
   * Variable eventName
   */
  private String eventName;
  /**
   * Variable eventStart
   */
  private String eventStart;
  /**
   * Variable eventEnd
   */
  private String eventEnd;

  /**
   * @return the eventName
   */
  public String getEventName()
  {
    return eventName;
  }

  /**
   * @param eventName the eventName to set
   */
  public void setEventName(String eventName)
  {
    this.eventName = eventName;
  }

  /**
   * @return the eventStart
   */
  public String getEventStart()
  {
    return eventStart;
  }

  /**
   * @param eventStart the eventStart to set
   */
  public void setEventStart(String eventStart)
  {
    this.eventStart = eventStart;
  }

  /**
   * @return the eventEnd
   */
  public String getEventEnd()
  {
    return eventEnd;
  }

  /**
   * @param eventEnd the eventEnd to set
   */
  public void setEventEnd(String eventEnd)
  {
    this.eventEnd = eventEnd;
  }
}
