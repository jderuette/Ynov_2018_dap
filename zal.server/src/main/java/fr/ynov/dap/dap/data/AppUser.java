package fr.ynov.dap.dap.data;

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
	private int id;

	private String userkey;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="owner")
	private List<GoogleAccount> googleAccounts;
	
	
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

	public void addGoogleAccount(GoogleAccount account){
	    account.setOwner(this);
	    this.getGoogleAccounts().add(new GoogleAccount());
	}
}