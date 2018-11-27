package fr.ynov.dap.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import javax.persistence.Id;

@Entity
public class AppUser {
	@Id
	@GeneratedValue
	Integer id;
	
	String userKey;

	@OneToMany(cascade = CascadeType.ALL, mappedBy="owner")
	List<GoogleAccount> googleAccounts;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="owner")
	List<MicrosoftAccount> microsoftAccounts;
	
	public AppUser() {
		super();
		googleAccounts = new ArrayList<GoogleAccount>();
		microsoftAccounts = new ArrayList<MicrosoftAccount>();
	}

	public void addGoogleAccount(GoogleAccount account){
	    account.setOwner(this);
	    this.googleAccounts.add(account);
	}
	
	public List<GoogleAccount> getGoogleAccounts() {
		return googleAccounts;
	}

	public List<MicrosoftAccount> getMicrosoftAccounts() {
		return microsoftAccounts;
	}

	public void addMicrosoftAccounts(MicrosoftAccount account) {
		account.setOwner(this);
		this.microsoftAccounts.add(account);
	}

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
}
