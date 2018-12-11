package fr.ynov.dap.data;

import javax.persistence.*;

@Entity
public class GoogleAccount {
	@Id
	@GeneratedValue
	int id;

	@ManyToOne
	AppUser owner;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AppUser getOwner() {
		return owner;
	}

	public void setOwner(AppUser owner) {
		this.owner = owner;
	}

}
