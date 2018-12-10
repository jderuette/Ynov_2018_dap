package fr.ynov.dap.dap.data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class MicrosoftAccount {

	@Id
	@GeneratedValue
	int id;
	
	String accountName;
	
	String tenantId;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "owner")
	private Token token;
	
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
	
	public void setToken(Token token)
	{
		this.token = token;
		token.setOwner(this);
	}
	
	public Token getToken()
	{
		return this.token;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
	public String getTenantId() {
		return this.tenantId;
	}
}
