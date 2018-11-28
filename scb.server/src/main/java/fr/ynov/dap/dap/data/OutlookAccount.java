package fr.ynov.dap.dap.data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import fr.ynov.dap.dap.models.IdToken;
import fr.ynov.dap.dap.models.TokenResponse;

@Entity
public class OutlookAccount extends Account{
	@ManyToOne
	AppUser owner;
	String name;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "owner")
	TokenResponse idToken;
	
	public void setOwner(AppUser appUser) {
		this.owner = appUser;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String getOwnerName() {
		return owner.getName();
	}

	public void setIdToken(TokenResponse idToken) {
		this.idToken = idToken;
		this.idToken.setOwner(this);
	}
	
	public TokenResponse getIdToken() {
		return idToken;
	}
	
	
	
}

