package fr.ynov.dap.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import fr.ynov.dap.model.Google.GoogleAccountModel;
import fr.ynov.dap.model.microsoft.OutlookAccountModel;

@Entity
public class AppUserModel {

	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(unique=true)
	private String userKey;
	
	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}


	@OneToMany(cascade = CascadeType.ALL, mappedBy = "appUser")
	public List<GoogleAccountModel> googleAccounts;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "appUser")
	public List<OutlookAccountModel> microsoftAccounts;
	

	public List<OutlookAccountModel> getMicrosoftAccounts() {
		return microsoftAccounts;
	}

	public void setMicrosoftAccounts(List<OutlookAccountModel> microsoftAccounts) {
		this.microsoftAccounts = microsoftAccounts;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public List<GoogleAccountModel> getGoogleAccounts() {
		return googleAccounts;
	}

	public void setGoogleAccounts(List<GoogleAccountModel> googleAccounts) {
		this.googleAccounts = googleAccounts;
	}
	
	
	public void addGoogleAccount(GoogleAccountModel account){
	    account.setOwner(this);
	    googleAccounts.add(account);
	}
	
	public void addOutlookAccount(OutlookAccountModel account) {
		account.setUser(this);
		microsoftAccounts.add(account);
	}
}
