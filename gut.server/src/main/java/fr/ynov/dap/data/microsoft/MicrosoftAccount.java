package fr.ynov.dap.data.microsoft;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.ynov.dap.data.AppUser;

@Entity
public class MicrosoftAccount {

	private static final Logger logger = LogManager.getLogger();

	@Id
	@GeneratedValue
	private Integer id;
	
	private String accessToken;

	private String accountName;

	private String userTenantID;

	@ManyToOne
	AppUser appUser;

	public MicrosoftAccount(String name, String accessToken, String tenantId) {
		// TODO Auto-generated constructor stub
		this.accountName = name;
		this.accessToken = accessToken;
		this.userTenantID = tenantId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getUserTenantID() {
		return userTenantID;
	}

	public void setUserTenantID(String userTenantID) {
		this.userTenantID = userTenantID;
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public Integer getId() {
		return id;
	}

	public void setOwner(AppUser appUser) {
		logger.debug("Binding AppUser <-> MicrosoftAccount");
		this.appUser = appUser;
		if (!appUser.getMicrosoftAccounts().contains(this)) { // warning this may cause performance issues if you have a
																// large data set since this operation is O(n)
			appUser.getMicrosoftAccounts().add(this);
		}
	}

}
