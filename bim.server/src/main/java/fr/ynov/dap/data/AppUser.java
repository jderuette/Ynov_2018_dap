package fr.ynov.dap.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import fr.ynov.dap.data.google.GoogleAccount;
import fr.ynov.dap.data.microsoft.MicrosoftAccount;

/**
 * AppUser table.
 * @author MBILLEMAZ
 *
 */
@Entity
public class AppUser {

    /**
     * Sql Id.
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * Username.
     */
    private String name;

    /**
     * user google accounts.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<GoogleAccount> googleAccounts;

    /**
     * user microsoft accounts.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<MicrosoftAccount> microsoftAccounts;

    /**
     * Constructor.
     * @param name
     */
    public AppUser(String name) {
        this.name = name;
    }

    public AppUser() {

    }

    /**
     * Add google account to application.
     * @param account account to add
     */
    public void addGoogleAccount(final GoogleAccount account) {
        account.setOwner(this);
        this.getGoogleAccount().add(account);
    }

    /**
     * @return the microsoftAccount
     */
    public List<MicrosoftAccount> getMicrosoftAccounts() {
        return microsoftAccounts;
    }

    /**
     * @param microsoftAccount the microsoftAccount to set
     */
    public void setMicrosoftAccounts(List<MicrosoftAccount> microsoftAccount) {
        this.microsoftAccounts = microsoftAccounts;
    }

    /**
     * Add google account to application.
     * @param account account to add
     */
    public void addMicrosoftAccount(final MicrosoftAccount account) {
        account.setOwner(this);
        this.getMicrosoftAccounts().add(account);
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param newId the id to set
     */
    public void setId(final Integer newId) {
        this.id = newId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param newName the name to set
     */
    public void setName(final String newName) {
        this.name = newName;
    }

    /**
     * @return the googleAccount
     */
    public List<GoogleAccount> getGoogleAccount() {
        return googleAccounts;
    }

    /**
     * @param newGoogleAccount the googleAccount to set
     */
    public void setGoogleAccount(final List<GoogleAccount> newGoogleAccount) {
        this.googleAccounts = newGoogleAccount;
    }

}
