package fr.ynov.dap.google.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import fr.ynov.dap.microsoft.data.MicrosoftAccount;


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
	@OneToMany(cascade = CascadeType.ALL, mappedBy="owner")
	private List<GoogleAccount> googleAccounts;
	
	/** The microsoft accounts. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy="owner")
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
	 * @param id the new id
	 */
	public void setId(int id) {
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
	 * @param userKey the new user key
	 */
	public void setUserKey(String userKey) {
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
	 * @param googleAccounts the new google accounts
	 */
	public void setGoogleAccounts(List<GoogleAccount> googleAccounts) {
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
	public void setMicrosoftAccounts(List<MicrosoftAccount> microsoftAccounts) {
		this.microsoftAccounts = microsoftAccounts;
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
	 * Adds the ms account.
	 *
	 * @param account the account
	 */
	public void addMsAccount(MicrosoftAccount account){
	    account.setOwner(this);
	    this.getMicrosoftAccounts().add(account);
	}
}
