
package fr.ynov.dap.web.microsoft.service;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * Récupère l'utilisateur outlook
 * 
 * @author antod
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutlookUser
{
  /**
   * Variable id
   */
  private String id;
  /**
   * Variable mail
   */
  private String mail;
  /**
   * Variable displayName
   */
  private String displayName;

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
   * @return the mail
   */
  public String getMail()
  {
    return mail;
  }

  /**
   * @param mail the mail to set
   */
  public void setMail(String mail)
  {
    this.mail = mail;
  }

  /**
   * @return the displayName
   */
  public String getDisplayName()
  {
    return displayName;
  }

  /**
   * @param displayName the displayName to set
   */
  public void setDisplayName(String displayName)
  {
    this.displayName = displayName;
  }
}
