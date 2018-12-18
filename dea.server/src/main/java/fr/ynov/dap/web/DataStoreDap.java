
package fr.ynov.dap.web;


/**
 * Classe permettant d'afficher le dataStore dans notre application
 * 
 * @author antod
 *
 */
//TODO dea by Djer |POO| Cette classe devrait plutot s'appeler "CredetialDap". Un "dataStore" est un "empalcment" pour stocker des données. un "Credential" est une "identification"
public class DataStoreDap
{
  /**
   * Variable userName
   */
  private String userName;
  /**
   * Variable token
   */
  private String token;
  /**
   * Variable credentialType
   */
  private String credentialType;
  /**
   * Variable tenantId
   */
  private String tenantId;
  /**
   * Variable mailUrl
   */
  private String mailUrl;

  /**
   * @return the userName
   */
  public String getUserName()
  {
    return userName;
  }

  /**
   * @param userName the userName to set
   */
  public void setUserName(String userName)
  {
    this.userName = userName;
  }

  /**
   * @return the token
   */
  public String getToken()
  {
    return token;
  }

  /**
   * @param token the token to set
   */
  public void setToken(String token)
  {
    this.token = token;
  }

  /**
   * @return the credentialType
   */
  public String getCredentialType()
  {
    return credentialType;
  }

  /**
   * @param credentialType the credentialType to set
   */
  public void setCredentialType(String credentialType)
  {
    this.credentialType = credentialType;
  }

  /**
   * @return the tenantId
   */
  public String getTenantId()
  {
    return tenantId;
  }

  /**
   * @param tenantId the tenantId to set
   */
  public void setTenantId(String tenantId)
  {
    this.tenantId = tenantId;
  }

  /**
   * @return the mailUrl
   */
  public String getMailUrl()
  {
    return mailUrl;
  }

  /**
   * @param mailUrl the mailUrl to set
   */
  public void setMailUrl(String mailUrl)
  {
    this.mailUrl = mailUrl;
  }
}
