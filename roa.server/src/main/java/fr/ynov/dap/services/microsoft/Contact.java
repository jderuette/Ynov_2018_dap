package fr.ynov.dap.services.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Model de l'objet Contact de Microsoft.
 * @author alexa
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact {
  /**
   * id.
   */
  private String id;
  /**
   * nom.
   */
  private String givenName;
  /**
   * nom de famille.
   */
  private String surname;
  /**
   * nom de la compagnie.
   */
  private String companyName;
  /**
   * liste des adresses mail.
   */
  private EmailAddress[] emailAddresses;

  /**
   * récupère l'ID.
   * @return String id
   */
  public String getId() {
    return id;
  }
  /**
   * set id.
   * @param iid id
   */
  public void setId(final String iid) {
    this.id = iid;
  }
  /**
   * récupère le nom.
   * @return string nom
   */
  public String getGivenName() {
    return givenName;
  }
  /**
   * set le nom.
   * @param ggivenName name
   */
  public void setGivenName(final String ggivenName) {
    this.givenName = ggivenName;
  }
  /**
   * récupère le nom de famille.
   * @return string surname
   */
  public String getSurname() {
    return surname;
  }
  /**
   * set le nom de famille.
   * @param ssurname nom de famille
   */
  public void setSurname(final String ssurname) {
    this.surname = ssurname;
  }
  /**
   * récupère le nom de la compagnie.
   * @return String compagnieName
   */
  public String getCompanyName() {
    return companyName;
  }
  /**
   * set le nom de la compagnie.
   * @param ccompanyName String
   */
  public void setCompanyName(final String ccompanyName) {
    this.companyName = ccompanyName;
  }
  /**
   * récupère l'adresse mail.
   * @return string[] emailAddresses.
   */
  public EmailAddress[] getEmailAddresses() {
    return emailAddresses;
  }
  /**
   * set l'adresse mail.
   * @param eemailAddresses liste d'adresse mail
   */
  public void setEmailAddresses(final EmailAddress[] eemailAddresses) {
    this.emailAddresses = eemailAddresses;
  }
}
