package fr.ynov.dap.GoogleMaven.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import fr.ynov.dap.GoogleMaven.data.GoogleAccount;

@Entity
public class AppUser {


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int id;


	String userKey;

	@OneToMany(cascade = CascadeType.ALL, mappedBy="owner")
	List<GoogleAccount> googleAccounts;


	public AppUser() {}

	public AppUser(String userKey) {
		
		this.userKey = userKey;
	}


	public void adGoogleAccount(GoogleAccount account){
		account.setOwner(this);
		this.googleAccounts.add(account);
	}

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public List<GoogleAccount> getGoogleAccounts() {
		return googleAccounts;
	}

	public void setGoogleAccounts(List<GoogleAccount> googleAccounts) {
		this.googleAccounts = googleAccounts;
	}

	public int getId() {
		return id;
	}

	
	
	
}
