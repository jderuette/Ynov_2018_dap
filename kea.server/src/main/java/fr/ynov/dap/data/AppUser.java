package fr.ynov.dap.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import fr.ynov.dap.data.GoogleAccount;

@Entity
public class AppUser {
  @Id
  @GeneratedValue
  private int id;
  private String userKey;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "appUser")
  private List<GoogleAccount> googleAccounts;

  public String getUserKey() {
    return userKey;
  }

  public void setUserKey(String userKey) {
    this.userKey = userKey;
  }

  public List<GoogleAccount> getGoogleAccounts() {
    return googleAccounts;
  }

  public void setGoogleAccounts(List<GoogleAccount> googleAccounts) {
    this.googleAccounts = googleAccounts;
  }

  public int getId() {
    return id;
  }

  public void addGoogleAccount(GoogleAccount account) {
    account.setAppUser(this);
    this.getGoogleAccounts().add(account);
  }
}
