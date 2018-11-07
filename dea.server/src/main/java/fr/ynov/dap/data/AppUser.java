
package fr.ynov.dap.data;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class AppUser
{
  @Id
  @GeneratedValue
  private Integer id;
  private String name;

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
  private List<GoogleAccount> googleAccounts = new ArrayList<GoogleAccount>();

  public List<GoogleAccount> getAccounts()
  {
    return googleAccounts;
  }

  public void addGoogleAccount(GoogleAccount account)
  {
    account.setOwner(this);

    this.getAccounts().add(account);
  }
}
