
package fr.ynov.dap.web.microsoft.service;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * Classe Recipent
 * 
 * @author antod
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipient
{
  /**
   * Classe meailAdress
   */
  private EmailAddress emailAddress;

  /**
   * @return the emailAddress
   */
  public EmailAddress getEmailAddress()
  {
    return emailAddress;
  }

  /**
   * @param emailAddress the emailAddress to set
   */
  public void setEmailAddress(EmailAddress emailAddress)
  {
    this.emailAddress = emailAddress;
  }

}
