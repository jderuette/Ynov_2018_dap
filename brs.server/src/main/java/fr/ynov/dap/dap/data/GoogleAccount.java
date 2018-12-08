package fr.ynov.dap.dap.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class GoogleAccount {
	
	
    //TODO brs by Djer |POO| Attention à l'ordre (ordre  : constantes, attributs, initialisateurs statics, constructeurs, méthodes métiers, méthodes "génériques" (toString, hashCode,...) getter/steer)
	public GoogleAccount() {
		super();
	}

	

	public GoogleAccount(String accountName) {
		this.accountName = accountName;
	}

	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	private AppUser Owner;
	
	@Column
	private String accountName;
	
	
	
	public String getAccountName() {
		return accountName;
	}



	public void setOwner(AppUser appUser) {
		
		this.Owner = appUser;
		
	}



	public void setAccountName(String accountName2) {
		this.accountName = accountName2;
		
	}
}
