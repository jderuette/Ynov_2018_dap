package fr.ynov.dap.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import javax.persistence.Id;

/**
 * The Class AppUser.
 */
@Entity
public class AppUser {

	/** The id. */
	@Id
	@GeneratedValue
	Integer id;

	/** The user key. */
	String userKey;

	/** The google accounts. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	List<GoogleAccount> googleAccounts;

	/** The microsoft accounts. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	List<MicrosoftAccount> microsoftAccounts;

	/**
	 * Instantiates a new app user.
	 */
	public AppUser() {
		super();
		googleAccounts = new ArrayList<GoogleAccount>();
		microsoftAccounts = new ArrayList<MicrosoftAccount>();
	}

	/**
	 * Adds the google account.
	 *
	 * @param account the account
	 */
	public void addGoogleAccount(GoogleAccount account) {
		account.setOwner(this);
		this.googleAccounts.add(account);
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
	 * Gets the microsoft accounts.
	 *
	 * @return the microsoft accounts
	 */
	public List<MicrosoftAccount> getMicrosoftAccounts() {
		return microsoftAccounts;
	}

	/**
	 * Adds the microsoft accounts.
	 *
	 * @param account the account
	 */
	public void addMicrosoftAccounts(MicrosoftAccount account) {
		account.setOwner(this);
		this.microsoftAccounts.add(account);
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
	 * Sets the user key.
	 *
	 * @param userKey the new user key
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
}
