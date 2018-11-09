package fr.ynov.dap.dap.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
}
