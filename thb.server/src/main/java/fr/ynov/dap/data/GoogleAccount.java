package fr.ynov.dap.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.Id;

@Entity
public class GoogleAccount {
	@Id
	@GeneratedValue
	Integer id;
	
	@ManyToOne
	AppUser owner;
	
	String name;

	public void setOwner(AppUser owner) {
		this.owner = owner;
	}

	public AppUser getOwner() {
		return owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
