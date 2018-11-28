package fr.ynov.dap.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import fr.ynov.dap.data.google.GoogleAccount;
import fr.ynov.dap.data.microsoft.MicrosoftAccount;


@Entity(name = "appUsers")
public class AppUser {
	
	@Id
	@GeneratedValue
	private Integer id;   
	
	@Column(unique=true)
	private String userKey;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="appUser")
	private List<GoogleAccount> googleAccounts;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="appUser")
	private List<MicrosoftAccount> microsoftAccounts;
	
	public void addGoogleAccount(GoogleAccount account) {
		account.setOwner(this);
		this.getGoogleAccounts().add(account);
	}
	
	public void addMicrosoftAccount(MicrosoftAccount account) {
		account.setOwner(this);
		this.getMicrosoftAccounts().add(account);
	}

	public AppUser() {}
	
	public AppUser(String _userKey) {
		this.userKey = _userKey;
		this.googleAccounts = new ArrayList<GoogleAccount>();
	}

	public String getUserKey() {
		return userKey;
	}


	public void setName(String userKey) {
		this.userKey = userKey;
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

	public Integer getId() {
		return id;
	}

	
	
	
}
