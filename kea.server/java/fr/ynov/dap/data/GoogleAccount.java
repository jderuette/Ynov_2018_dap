package fr.ynov.dap.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class GoogleAccount {
  @Id
  @GeneratedValue
  private int id;
  @ManyToOne
  private AppUser appUser;

  public AppUser getAppUser() {
    return appUser;
  }

  public void setAppUser(AppUser appUser) {
    this.appUser = appUser;
  }

  public int getId() {
    return id;
  }
}
