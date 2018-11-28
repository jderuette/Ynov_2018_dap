package fr.ynov.dap.dap.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class OutlookEvent.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutlookEvent {
  
  /** The id. */
  private String id;
  
  /** The subject. */
  private String subject;
  
  /** The organizer. */
  private OutlookRecipient organizer;
  
  /** The start. */
  private OutlookDateTimeTimeZone start;
  
  /** The end. */
  private OutlookDateTimeTimeZone end;

  /**
   * Gets the id.
   *
   * @return the id
   */
  public String getId() {
    return id;
  }
  
  /**
   * Sets the id.
   *
   * @param id the new id
   */
  public void setId(String id) {
    this.id = id;
  }
  
  /**
   * Gets the subject.
   *
   * @return the subject
   */
  public String getSubject() {
    return subject;
  }
  
  /**
   * Sets the subject.
   *
   * @param subject the new subject
   */
  public void setSubject(String subject) {
    this.subject = subject;
  }
  
  /**
   * Gets the organizer.
   *
   * @return the organizer
   */
  public OutlookRecipient getOrganizer() {
    return organizer;
  }
  
  /**
   * Sets the organizer.
   *
   * @param organizer the new organizer
   */
  public void setOrganizer(OutlookRecipient organizer) {
    this.organizer = organizer;
  }
  
  /**
   * Gets the start.
   *
   * @return the start
   */
  public OutlookDateTimeTimeZone getStart() {
    return start;
  }
  
  /**
   * Sets the start.
   *
   * @param start the new start
   */
  public void setStart(OutlookDateTimeTimeZone start) {
    this.start = start;
  }
  
  /**
   * Gets the end.
   *
   * @return the end
   */
  public OutlookDateTimeTimeZone getEnd() {
    return end;
  }
  
  /**
   * Sets the end.
   *
   * @param end the new end
   */
  public void setEnd(OutlookDateTimeTimeZone end) {
    this.end = end;
  }
}
