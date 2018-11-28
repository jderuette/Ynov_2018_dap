package fr.ynov.dap.dap.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * The Class AppUser.
 */
@Entity
public class AppUser {

	/** The id. */
	@Id
	@GeneratedValue
	private int id;

	/** The name. */
	private String name;
	
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
	public void setName(String name) {
		this.name = name;
	}

	/** The google account. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<GoogleAccount> googleAccount;
	
	/** The microsoft account. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<OutlookAccount> microsoftAccount;
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
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
	 * Gets the google accounts.
	 *
	 * @return the google accounts
	 */
	public List<GoogleAccount> getGoogleAccounts() {
		return googleAccount;
	}

	/**
	 * Adds the microsoft account.
	 *
	 * @param account the account
	 */
	public void addMicrosoftAccount(OutlookAccount account){
	    account.setOwner(this);
	    this.getOutlookAccounts().add(account);
	}

	/**
	 * Gets the outlook accounts.
	 *
	 * @return the outlook accounts
	 */
	public List<OutlookAccount> getOutlookAccounts() {
		return microsoftAccount;
	}

	
}
