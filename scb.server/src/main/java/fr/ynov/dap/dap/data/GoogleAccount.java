package fr.ynov.dap.dap.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class GoogleAccount extends Account{
	@Id
	@GeneratedValue
	int id;
	
	@ManyToOne
	AppUser owner;
	String name;
	
	public void setOwner(AppUser appUser) {
		this.owner = appUser;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	
	
}
