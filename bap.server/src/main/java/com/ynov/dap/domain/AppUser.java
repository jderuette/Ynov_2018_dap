package com.ynov.dap.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.ynov.dap.domain.google.GoogleAccount;
import com.ynov.dap.domain.microsoft.MicrosoftAccount;

/**
 * The Class AppUser.
 */
@Entity
public class AppUser {

    /** The id. */
    @Id
    @GeneratedValue
    private Integer id;

    /** The name. */
    private String name;

    /** The google accounts. */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<GoogleAccount> googleAccounts;

    /** The microsoft accounts. */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<MicrosoftAccount> microsoftAccounts;

    /**
     * Adds the google account.
     *
     * @param account the account
     */
    public void addGoogleAccount(final GoogleAccount account) {
        account.setOwner(this);
        this.getGoogleAccounts().add(account);
    }

    /**
     * Adds the microsoft account.
     *
     * @param account the account
     */
    public void addMicrosoftAccount(final MicrosoftAccount account) {
        account.setOwner(this);
        this.getMicrosoftAccounts().add(account);
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Gets the google accounts.
     *
     * @return the google accounts
     */
    public List<GoogleAccount> getGoogleAccounts() {
        return googleAccounts;
    }

    /**
     * Sets the google accounts.
     *
     * @param googleAccounts the new google accounts
     */
    public void setGoogleAccounts(final List<GoogleAccount> googleAccounts) {
        this.googleAccounts = googleAccounts;
    }

    /**
     * Gets the microsoft accounts.
     *
     * @return the microsoft accounts
     */
    public List<MicrosoftAccount> getMicrosoftAccounts() {
        return microsoftAccounts;
    }

    /**
     * Sets the microsoft accounts.
     *
     * @param microsoftAccounts the new microsoft accounts
     */
    public void setMicrosoftAccounts(final List<MicrosoftAccount> microsoftAccounts) {
        this.microsoftAccounts = microsoftAccounts;
    }

}
