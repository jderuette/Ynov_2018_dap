package fr.ynov.dap.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * ToDo.
 * @author Kévin Sibué
 *
 */
@Entity
public class AppUser {

    /**
     * Unique.
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * Name column.
     */
    @Column
    private String userKey;

    /**
     * List of every google account for this user.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<GoogleAccount> googleAccounts;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param val the id to set
     */
    public void setId(final Integer val) {
        this.id = val;
    }

    /**
     * @return the name
     */
    public String getUserKey() {
        return userKey;
    }

    /**
     * @param val the name to set
     */
    public void setUserKey(final String val) {
        this.userKey = val;
    }

    /**
     * @return the googleAccounts
     */
    public List<GoogleAccount> getGoogleAccounts() {
        return googleAccounts;
    }

    /**
     * @param val the googleAccounts to set
     */
    public void setGoogleAccounts(final List<GoogleAccount> val) {
        this.googleAccounts = val;
    }

    /**
     * Add new google account to current user.
     * @param account Google account to add
     */
    public void addGoogleAccount(final GoogleAccount account) {
        account.setOwner(this);
        this.googleAccounts.add(account);
    }

}
