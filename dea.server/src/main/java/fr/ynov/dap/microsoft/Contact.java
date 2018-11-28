
package fr.ynov.dap.microsoft;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.ynov.dap.web.microsoft.service.EmailAddress;


/**
 * Classe pour les contacts de microsoft
 * 
 * @author antod
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact
{
  private String id;
  private String givenName;
  private String surname;
  private String companyName;
  private EmailAddress[] emailAddresses;

  /**
   * Récupération de l'id
   * 
   * @return
   */
  public String getId()
  {
    return id;
  }

  /**
   * Assignation de l'Id
   * 
   * @param id
   */
  public void setId(String id)
  {
    this.id = id;
  }

  /**
   * Récupération du givenName
   * 
   * @return
   */
  public String getGivenName()
  {
    return givenName;
  }

  /**
   * Assignatino du givenName
   * 
   * @param givenName
   */
  public void setGivenName(String givenName)
  {
    this.givenName = givenName;
  }

  /**
   * Récupération du surname
   * 
   * @return
   */
  public String getSurname()
  {
    return surname;
  }

  /**
   * Assignation du surname
   * 
   * @param surname
   */
  public void setSurname(String surname)
  {
    this.surname = surname;
  }

  /**
   * Récupération du companyName
   * 
   * @return
   */
  public String getCompanyName()
  {
    return companyName;
  }

  /**
   * Assignation du companyName
   * 
   * @param companyName
   */
  public void setCompanyName(String companyName)
  {
    this.companyName = companyName;
  }

  /**
   * Récupération des EmailAddress
   * 
   * @return
   */
  public EmailAddress[] getEmailAddresses()
  {
    return emailAddresses;
  }

  /**
   * Assignation des EmailAddress
   * 
   * @param emailAddresses
   */
  public void setEmailAddresses(EmailAddress[] emailAddresses)
  {
    this.emailAddresses = emailAddresses;
  }
}
