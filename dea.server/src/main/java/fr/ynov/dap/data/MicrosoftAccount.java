
package fr.ynov.dap.data;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import fr.ynov.dap.web.microsoft.auth.TokenResponse;


/**
 * Entité des comptes Microsoft
 * 
 * @author antod
 *
 */
@Entity
public class MicrosoftAccount
{
  /**
   * id du compte microsoft
   */
  @Id
  @GeneratedValue
  private Integer id;

  /**
   * onwer du compte microsoft
   */
  @ManyToOne
  private AppUser owner;

  /**
   * token de réponse
   */
  @OneToOne(cascade = CascadeType.ALL)
  private TokenResponse tokenResponse;

  /**
   * userName du compt microsoft
   */
  private String userName;

  /**
   * tenantId du compte microsoft
   */
  private String tenantId;

  /**
   * Récupère l'id
   * 
   * @return
   */
  public Integer getId()
  {
    return id;
  }

  /**
   * Change l'id
   * 
   * @param id
   */
  public void setId(Integer id)
  {
    this.id = id;
  }

  /**
   * Récupère le tokenResponse
   * 
   * @return
   */
  public TokenResponse getTokenResponse()
  {
    return tokenResponse;
  }

  /**
   * Change le tokenResponse
   * 
   * @param tokenResponse
   */
  public void setTokenResponse(TokenResponse tokenResponse)
  {
    this.tokenResponse = tokenResponse;
  }

  /**
   * Récupère le owner
   * 
   * @return
   */
  public AppUser getOwner()
  {
    return owner;
  }

  /**
   * Change le owner
   * 
   * @param owner
   */
  public void setOwner(AppUser owner)
  {
    this.owner = owner;
  }

  /**
   * Récupère le userName
   * 
   * @return
   */
  public String getUserName()
  {
    return userName;
  }

  /**
   * Change le userName
   * 
   * @param userName
   */
  public void setUserName(String userName)
  {
    this.userName = userName;
  }

  /**
   * Récupère le tenantId
   * 
   * @return
   */
  public String getTenantId()
  {
    return tenantId;
  }

  /**
   * Change le tenant Id
   * 
   * @param tenantId
   */
  public void setTenantId(String tenantId)
  {
    this.tenantId = tenantId;
  }
}
