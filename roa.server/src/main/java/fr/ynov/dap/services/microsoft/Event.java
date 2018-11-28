package fr.ynov.dap.services.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * évènement de calendrier de microsoft.
 * @author alexa
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {
  /**
   * id.
   */
  private String id;
  /**
   * sujet de l'évènement.
   */
  private String subject;
  /**
   * organisateur de l'évènement.
   */
  private Recipient organizer;
  /**
   * date et heure de début de l'évènement.
   */
  private DateTimeTimeZone start;
  /**
   * date et heure de fin de l'évènement.
   */
  private DateTimeTimeZone end;

  /**
   * récupère l'ID.
   * @return String id
   */
  public String getId() {
    return id;
  }
  /**
   * set l'id.
   * @param iid id
   */
  public void setId(final String iid) {
    this.id = iid;
  }
  /**
   * récupère le sujet de l'évènement.
   * @return String
   */
  public String getSubject() {
    return subject;
  }
  /**
   * set le sujet de l(évènement.
   * @param ssubject subject
   */
  public void setSubject(final String ssubject) {
    this.subject = ssubject;
  }
  /**
   * récupère l'organisateur de l'évènement.
   * @return Recipient
   */
  public Recipient getOrganizer() {
    return organizer;
  }
  /**
   * set l'organisateur de l'évènement.
   * @param oorganizer organizer
   */
  public void setOrganizer(final Recipient oorganizer) {
    this.organizer = oorganizer;
  }
  /**
   * récupérer la date de début.
   * @return DateTimeTimeZone
   */
  public DateTimeTimeZone getStart() {
    return start;
  }
  /**
   * set la date de départ.
   * @param sstart start
   */
  public void setStart(final DateTimeTimeZone sstart) {
    this.start = sstart;
  }
  /**
   * récupère la date de fin.
   * @return DateTimeTimeZone
   */
  public DateTimeTimeZone getEnd() {
    return end;
  }
  /**
   * set la date de fin.
   * @param eend end
   */
  public void setEnd(final DateTimeTimeZone eend) {
    this.end = eend;
  }
}
