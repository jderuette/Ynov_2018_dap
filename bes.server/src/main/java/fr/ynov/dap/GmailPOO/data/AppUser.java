package fr.ynov.dap.GmailPOO.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class AppUser {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	public String userKey;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private java.util.List<Account> accounts;

	public void addGoogleAccount(Account account) {
		account.setOwner(this);
		this.accounts.add(account);

	}

	public AppUser() {
	}

	public AppUser(String userKey) {
		this.userKey = userKey;

	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param userkey
	 * @param googleAccounts
	 */
	public AppUser(String userkey, List<Account> accounts) {
		super();
		this.userKey = userkey;
		this.accounts = accounts;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the userkey
	 */
	public String getUserkey() {
		return userKey;
	}

	/**
	 * @param userkey the userkey to set
	 */
	public void setUserkey(String userkey) {
		this.userKey = userkey;
	}
}
