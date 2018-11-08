
package fr.ynov.dap.data;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Entité des compte GoogleAccount
 * @author antod
 *
 */
@Entity
public class GoogleAccount
{
  /**
   * id du GoogleAccount
   */
  @Id
  @GeneratedValue
  private Integer id;

  /**
   * Owner du GoogleAccount
   */
  @ManyToOne
  private AppUser owner;
  /**
   * userName du GoogleAccount
   */
  private String userName;
  

  /**
   * Récupère le userName
   * @return
   */
  public String getUserName()
  {
    return userName;
  }

  /**
   * Change le userName
   * @param userName
   */
  public void setUserName(String userName)
  {
    this.userName = userName;
  }

  /**
   * Récupère l'Id
   * @return
   */
  public Integer getId()
  {
    return id;
  }

  /**
   * Change l'Id
   * @param id
   */
  public void setId(Integer id)
  {
    this.id = id;
  }

  /**
   * Récupère l'owner
   * @return
   */
  public AppUser getOwner()
  {
    return owner;
  }

  /**
   * Change l'owner
   * @param newOwner
   */
  public void setOwner(AppUser newOwner)
  {
    this.owner = newOwner;
  }
}
