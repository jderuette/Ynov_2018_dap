package fr.ynov.dap.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Model for AppUser.
 * @author thibault
 *
 */
@Entity
public class AppUser {

    /**
     * Id of entity.
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * User key.
     */
    @Column(unique = true)
    private String userKey;

    /**
     * Google accounts of user.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<GoogleAccount> googleAccounts;

    /**
     * Add a google account of this user.
     * @param account the GoogleAccount to add
     */
    public void adGoogleAccount(final GoogleAccount account) {
        account.setOwner(this);
        this.getGoogleAccounts().add(account);
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return the googleAccounts
     */
    public List<GoogleAccount> getGoogleAccounts() {
        return googleAccounts;
    }

    /**
     * @param gAccounts the googleAccounts to set
     */
    public void setGoogleAccounts(final List<GoogleAccount> gAccounts) {
        this.googleAccounts = gAccounts;
    }

    /**
     * @return the userKey
     */
    public String getUserKey() {
        return userKey;
    }

    /**
     * @param uKey the userKey to set
     */
    public void setUserKey(final String uKey) {
        this.userKey = uKey;
    }
}
