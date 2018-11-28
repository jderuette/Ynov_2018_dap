package fr.ynov.dap.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import fr.ynov.dap.model.google.GoogleAccount;
import fr.ynov.dap.model.microsoft.MicrosoftAccount;

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
    @Column(unique = true)
    private String userKey;

    /**
     * List of every google account for this user.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<GoogleAccount> googleAccounts;

    /**
     * List of every microsoft account for this user.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<MicrosoftAccount> microsoftAccounts;

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

    /**
     * @return the MicrosoftAccount
     */
    public List<MicrosoftAccount> getMicrosoftAccounts() {
        return microsoftAccounts;
    }

    /**
     * @param val the Microsoft account to set
     */
    public void setMicrosoftAccounts(final List<MicrosoftAccount> val) {
        this.microsoftAccounts = val;
    }

    /**
     * Add new Microsoft account to current user.
     * @param account Microsoft account to add
     */
    public void addMicrosoftAccount(final MicrosoftAccount account) {
        account.setOwner(this);
        this.microsoftAccounts.add(account);
    }

}
