package fr.ynov.dap.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import fr.ynov.dap.data.microsoft.MicrosoftAccount;

/**
 * The Class AppUser.
 */
@Entity
public class AppUser {

	/** The id. */
	@Id
	@GeneratedValue
	public int id;
	
	/** The user key. */
	@Column
	public String userKey;
	
	/** The google accounts. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy="owner")
	private List<GoogleAccount> googleAccounts;
	
	 /**
     * List of every microsoft account for this user.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<MicrosoftAccount> microsoftAccounts;
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the user key.
	 *
	 * @return the user key
	 */
	public String getUserKey() {
		return userKey;
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
	 * Sets the user key.
	 *
	 * @param userKey the new user key
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * Sets the google accounts.
	 *
	 * @param googleAccounts the new google accounts
	 */
	public void setGoogleAccounts(List<GoogleAccount> googleAccounts) {
		this.googleAccounts = googleAccounts;
	}
	
	/**
	 * Adds the google account.
	 *
	 * @param account the account
	 */
	public void addGoogleAccount(GoogleAccount account){
		
		account.setOwner(this);
		this.getGoogleAccounts().add(account);
		
	}
	
	/**
	 * Gets the microsoft accounts.
	 *
	 * @return the MicrosoftAccount
	 */
    public List<MicrosoftAccount> getMicrosoftAccounts() {
        return microsoftAccounts;
    }

    /**
	 * Sets the microsoft accounts.
	 *
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
