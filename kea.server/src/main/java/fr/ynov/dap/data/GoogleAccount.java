package fr.ynov.dap.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * the google account linked to the appUser in database.
 * @author Antoine
 *
 */
@Entity
public class GoogleAccount {
  /**
   * generated by JPA.
   */
  @Id
  @GeneratedValue
  private int id;
  /**
   * Many GoogleAccounts are linked to an appUser.
   */
  @ManyToOne
  private AppUser appUser;
  /**
   * the name of the google account.
   */
  private String googleAccountName;

  /**
   * get the unique identifier generated by Hibernate.
   * @return the appUser
   */
  public AppUser getAppUser() {
    return appUser;
  }

  /**
   * set the unique identifier generated by Hibernate.
   * @param applicationUser the appUser
   */
  public void setAppUser(final AppUser applicationUser) {
    this.appUser = applicationUser;
  }

  /**
   * get the name of the object.
   * @return a String that containes the name
   */
  public String getGoogleAccountName() {
    return googleAccountName;
  }

  /**
   * Sets the acountName.
   * @param gAccountName the new accountName to replace the old one
   */
  public void setGoogleAccountName(final String gAccountName) {
    this.googleAccountName = gAccountName;
  }

  /**
   * get the id generated value.
   * @return the id
   */
  public int getId() {
    return id;
  }
}
