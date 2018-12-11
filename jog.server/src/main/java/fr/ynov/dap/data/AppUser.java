package fr.ynov.dap.data;

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

	public String name;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	List<GoogleAccount> googleAccounts;

	public AppUser(String name) {
		super();
		this.name = name;
	}

	public void adGoogleAccount(GoogleAccount account) {
		account.setOwner(this);
		this.googleAccounts.add(account);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

}
