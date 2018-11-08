package fr.ynov.dap.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author alex
 *
 */
@Entity
public class GoogleAccount {
  /**
   * id.
   */
  @Id
  @GeneratedValue
  private Integer id;
  /**
   * compte google.
   */
  private String accountName;
  /**
   * propriétaire du compte google.
   */
  @ManyToOne
  private AppUser owner;
  /**
   * Set Owner.
   * @param appUser owner
   */
  public final void setOwner(final AppUser appUser) {
    this.owner = appUser;
  }
  /**
   * @param accountNam nom du compte
   */
  public final void setAccountName(final String accountNam) {
      this.accountName = accountNam;
  }
  /**
   * @return accountName
   */
  public final String getAccountName() {
      return this.accountName;
  }
}
