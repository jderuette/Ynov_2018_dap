package fr.ynov.dap.dap.data.google;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * The Class GoogleAccount.
 */
@Entity
public class GoogleAccount {

	@Id
	@GeneratedValue
	Integer id;

	@ManyToOne
	AppUser owner;

	String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOwner(AppUser appUser) {
	    //TODO mot by Djer |IDE| Pense à supprimer les TO-DO lorsqu'ils ne sont plus vrais.
		// TODO Auto-generated method stub
		this.owner = appUser;
	}

	public AppUser getOwner() {
		return owner;
	}
}
