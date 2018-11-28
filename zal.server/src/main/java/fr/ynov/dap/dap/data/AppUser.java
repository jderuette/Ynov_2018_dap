package fr.ynov.dap.dap.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import fr.ynov.dap.dap.data.google.GoogleAccount;
import fr.ynov.dap.dap.data.microsoft.MicrosoftAccount;

/**
 * The Class AppUser.
 */
@Entity
public class AppUser {

	/** The id. */
	@Id
	@GeneratedValue
	private int id;

	/** The userkey. */
	private String userkey;

	/** The google accounts. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<GoogleAccount> googleAccounts;

	/** The microsoft accounts. */
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
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(final int id) {
		this.id = id;
	}

	/**
	 * Gets the user key.
	 *
	 * @return the user key
	 */
	public String getUserKey() {
		return userkey;
	}

	/**
	 * Sets the user key.
	 *
	 * @param userKey
	 *            the new user key
	 */
	public void setUserKey(final String userKey) {
		this.userkey = userKey;
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
	 * @param googleAccounts
	 *            the new google accounts
	 */
	public void setGoogleAccounts(final List<GoogleAccount> googleAccounts) {
		this.googleAccounts = googleAccounts;
	}

	/**
	 * Adds the google account.
	 *
	 * @param account
	 *            the account
	 */
	public void addGoogleAccount(final GoogleAccount account) {
		account.setOwner(this);
		this.getGoogleAccounts().add(new GoogleAccount());
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
	 * @param val
	 *            the new microsoft accounts
	 */
	public void setMicrosoftAccounts(final List<MicrosoftAccount> val) {
		this.microsoftAccounts = val;
	}

	/**
	 * Adds the microsoft account.
	 *
	 * @param account
	 *            the account
	 */
	public void addMicrosoftAccount(final MicrosoftAccount account) {
		account.setOwner(this);
		this.microsoftAccounts.add(account);
	}
}