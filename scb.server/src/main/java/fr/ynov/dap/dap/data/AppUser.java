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
	int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setGoogleAccounts(List<GoogleAccount> googleAccounts) {
		this.googleAccounts = googleAccounts;
	}

	public List<GoogleAccount> getAccounts() {
		return googleAccounts;
	}
	
	public void setOutlookAccounts(List<OutlookAccount> outlookAccounts) {
		this.outlookAccounts = outlookAccounts;
	}

	public List<OutlookAccount> getOutlookAccounts() {
		return outlookAccounts;
	}

	String name;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	List<GoogleAccount> googleAccounts;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	List<OutlookAccount> outlookAccounts;

	public void addAccount(GoogleAccount account) {

		account.setOwner(this);

		this.getAccounts().add(account);

	}
	
	public void addAccount(OutlookAccount account) {

		account.setOwner(this);

		this.getOutlookAccounts().add(account);

	}

}
