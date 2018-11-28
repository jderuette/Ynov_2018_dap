package fr.ynov.dap.services.microsoft;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * gère les éléments date ainsi que les fuseaux horraires.
 * @author alexa
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DateTimeTimeZone {
  /**
   * dateTime.
   */
  private Date dateTime;
  /**
   * Timezone de la date.
   */
  private String timeZone;

  /**
   * récupère la date et l'heure.
   * @return Date
   */
  public Date getDateTime() {
    return dateTime;
  }
  /**
   * set la date et l'heure.
   * @param ddateTime date
   */
  public void setDateTime(final Date ddateTime) {
    this.dateTime = ddateTime;
  }
  /**
   * récupère la timezone.
   * @return String
   */
  public String getTimeZone() {
    return timeZone;
  }
  /**
   * set la timezone.
   * @param ttimeZone String
   */
  public void setTimeZone(final String ttimeZone) {
    this.timeZone = ttimeZone;
  }
}
