package fr.ynov.dap.dap.data;

import java.util.List;

import javax.persistence.*;

@Entity
public class AppUser {
	
	@Id
	@GeneratedValue
	int id;

	@Column(unique=true)
	String userKey;
	
	//TODO for by Djer |POO| Ne mélange pas tes attributs et tes getters/setter (ordre : constantes, attributs, initialisateur static, constructeur, méthpde métier, méthode "génériques" (toString, hashCode,...) getter/setter)
	
	public String getUserKey() {
		return userKey;
	}


	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}


	@OneToMany(cascade = CascadeType.ALL, mappedBy="owner")
	List<GoogleAccount> googleAccounts;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="owner")
	List<MicrosoftAccount> microsoftAccounts;
	
	public List<GoogleAccount> getGoogleAccounts() {
		return googleAccounts;
	}

	public void setGoogleAccounts(List<GoogleAccount> googleAccounts) {
		this.googleAccounts = googleAccounts;
	}

	public void addGoogleAccount(GoogleAccount account){
	    account.setOwner(this);
	    this.getGoogleAccounts().add(account);
	}
	
	
	public List<MicrosoftAccount> getMicrosoftAccounts() {
		return microsoftAccounts;
	}

	public void setMicrosoftAccounts(List<MicrosoftAccount> microsoftAccounts) {
		this.microsoftAccounts = microsoftAccounts;
	}

	public void addMicrosoftAccount(MicrosoftAccount account){
	    account.setOwner(this);
	    this.getMicrosoftAccounts().add(account);
	}
}
