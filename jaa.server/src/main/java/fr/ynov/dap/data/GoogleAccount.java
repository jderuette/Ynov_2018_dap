package fr.ynov.dap.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author adrij
 *
 */
@Entity
public class GoogleAccount {

    /**
     * GoogleAccount Id.
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * Owner of this GoogleAccount.
     */
    @ManyToOne
    private AppUser appUser;

    /**
     * Get the owner.
     * @return the owner of this GoogleAccount.
     */
    public AppUser getOwner() {
        return this.appUser;
    }

    /**
     * set the owner of this GoogleAccount.
     * @param user AppUser (owner) of this GoogleAccount.
     */
    public void setOwner(final AppUser user) {
        this.appUser = user;
    }

    /**
     * name of account.
     */
    @Column
    private String accountName;

    /**
     * Getter of AccountName.
     * @return String of AccountName.
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * Setter of AccountName.
     * @param name AccountName
     */
    public void setAccountName(final String name) {
        this.accountName = name;
    }

    /**
     *  Default Constructor.
     */
    public GoogleAccount() {
    }

}
