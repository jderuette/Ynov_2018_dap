package fr.ynov.dap.google.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import fr.ynov.dap.microsoft.data.MicrosoftAccount;


@Entity
public class AppUser {

	@Id
	@GeneratedValue
	private int id;

	private String userkey;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="owner")
	private List<GoogleAccount> googleAccounts;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="owner")
	private List<MicrosoftAccount> microsoftAccounts;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserKey() {
		return userkey;
	}

	public void setUserKey(String userKey) {
		this.userkey = userKey;
	}

	public List<GoogleAccount> getGoogleAccounts() {
		return googleAccounts;
	}

	public void setGoogleAccounts(List<GoogleAccount> googleAccounts) {
		this.googleAccounts = googleAccounts;
	}

	public List<MicrosoftAccount> getMicrosoftAccounts() {
		return microsoftAccounts;
	}

	public void setMicrosoftAccounts(List<MicrosoftAccount> microsoftAccounts) {
		this.microsoftAccounts = microsoftAccounts;
	}

	public void addGoogleAccount(GoogleAccount account){
	    account.setOwner(this);
	    this.getGoogleAccounts().add(account);
	}
	
	public void addMsAccount(MicrosoftAccount account){
	    account.setOwner(this);
	    this.getMicrosoftAccounts().add(account);
	}
}
