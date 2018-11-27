package fr.ynov.dap.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity(name = "appUsers")
public class AppUser {
	
	@Id
	@GeneratedValue
	private Integer id;   
	
	@Column(unique=true)
	private String userKey;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="appUser")
	private List<GoogleAccount> googleAccounts;
	
	
	public void addGoogleAccount(GoogleAccount account) {
		account.setOwner(this);
		this.getGoogleAccounts().add(account);
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


	public Integer getId() {
		return id;
	}
	
	
	
}
