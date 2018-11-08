package com.ynov.dap.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class AppUser {

	@Id
	@GeneratedValue
	private Integer id;

	private String name;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<GoogleAccount> googleAccounts;

	public void addGoogleAccount(GoogleAccount account){
	    account.setOwner(this);
	    this.getGoogleAccounts().add(account);
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

}
