
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
  @Id
  @GeneratedValue
  private Integer id;

  @ManyToOne
  private AppUser owner;
  private String userName;
  

  public String getUserName()
  {
    return userName;
  }

  public void setUserName(String userName)
  {
    this.userName = userName;
  }

  public Integer getId()
  {
    return id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  public AppUser getOwner()
  {
    return owner;
  }

  public void setOwner(AppUser newOwner)
  {
    this.owner = newOwner;
  }
}
