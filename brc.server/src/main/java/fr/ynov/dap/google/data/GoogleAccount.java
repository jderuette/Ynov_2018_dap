package fr.ynov.dap.google.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class GoogleAccount {

	@Id
	@GeneratedValue
	private int id;
	
	private String name;
	
	@ManyToOne
	private AppUser owner;

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

	public AppUser getOwner() {
		return owner;
	}

	public void setOwner(AppUser owner) {
		this.owner = owner;
	}
	
	
}
