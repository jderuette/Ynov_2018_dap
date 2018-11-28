package fr.ynov.dap.model.microsoft;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import fr.ynov.dap.auth.TokenResponse;
import fr.ynov.dap.model.AppUserModel;

@Entity
public class OutlookAccountModel {

	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	private AppUserModel appUser;
	
	private String accountName;
	
	private String tenantID;

	public String getTenantID() {
		return tenantID;
	}

	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
	}

	@OneToOne(cascade = CascadeType.ALL)
	private TokenResponse token;
	
	public TokenResponse getToken() {
		return token;
	}

	public void setToken(TokenResponse token) {
		token.setOwner(this);
		this.token = token;
	}

	public AppUserModel getUser() {
		return appUser;
	}

	public void setUser(AppUserModel user) {
		this.appUser = user;
	}


	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
}
