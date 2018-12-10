package fr.ynov.dap.dap.data;

import javax.persistence.*;

@Entity
public class GoogleAccount {

	@Id
	@GeneratedValue
	int id;
	
	String accountName;
	
	@Column(length = 2000)
	String token;
	
	@ManyToOne
	AppUser owner;

	public AppUser getOwner() {
		return owner;
	}

	public void setOwner(AppUser owner) {
		this.owner = owner;
	}
	
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public String getAccountName()
	{
		return this.accountName;
	}
	
	public void setToken(String token)
	{
		this.token = token;
	}
	
	public String getToken()
	{
		return this.token;
	}
}
