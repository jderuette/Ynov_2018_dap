package fr.ynov.dap.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.ynov.dap.data.AppUser; 

@Entity(name = "googleAccounts")
public class GoogleAccount {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private String accountName;
	
	@ManyToOne
	AppUser appUser;
	
	public GoogleAccount() {}
	
	public GoogleAccount(String _accountName){
		this.accountName = _accountName;
	}	

	public void setOwner(AppUser appUser) {
		logger.debug("Binding AppUser <-> GoogleAccount");
        this.appUser = appUser;        
        if (!appUser.getGoogleAccounts().contains(this)) { // warning this may cause performance issues if you have a large data set since this operation is O(n)
        	appUser.getGoogleAccounts().add(this);
        }
    }

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public Integer getId() {
		return id;
	}
	
	
	
}
