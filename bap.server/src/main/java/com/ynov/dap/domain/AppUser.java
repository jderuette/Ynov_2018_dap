package com.ynov.dap.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.ynov.dap.domain.google.GoogleAccount;
import com.ynov.dap.domain.microsoft.MicrosoftAccount;

@Entity
public class AppUser {

	@Id
	@GeneratedValue
	private Integer id;

	private String name;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<GoogleAccount> googleAccounts;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<MicrosoftAccount> microsoftAccounts;

	public void addGoogleAccount(GoogleAccount account){
	    account.setOwner(this);
	    this.getGoogleAccounts().add(account);
	}
	
	public void addMicrosoftAccount(MicrosoftAccount account){
	    account.setOwner(this);
	    this.getMicrosoftAccounts().add(account);
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}
