
package fr.ynov.dap.web;


public class DataStoreDap
{
  private String userName;
  private String token;
  private String credentialType;
  private String tenantId;
  private String mailUrl;

  public String getUserName()
  {
    return userName;
  }

  public void setUserName(String userName)
  {
    this.userName = userName;
  }

  public String getToken()
  {
    return token;
  }

  public void setToken(String token)
  {
    this.token = token;
  }

  public String getCredentialType()
  {
    return credentialType;
  }

  public void setCredentialType(String credentialType)
  {
    this.credentialType = credentialType;
  }

  public String getTenantId()
  {
    return tenantId;
  }

  public void setTenantId(String tenantId)
  {
    this.tenantId = tenantId;
  }

  public String getMailUrl()
  {
    return mailUrl;
  }

  public void setMailUrl(String mailUrl)
  {
    this.mailUrl = mailUrl;
  }
}
