package fr.ynov.dap.dap.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import fr.ynov.dap.dap.data.GoogleAccount;

@Entity
public class AppUser {
	public AppUser() {
		super();
		googleAccounts = new ArrayList<GoogleAccount>();
		microsoftAccounts = new ArrayList<MicrosoftAccount>();
	}
	
	@Id
	@GeneratedValue
	private Integer id;

	private String userkey;
	
	public String getUserkey() {
		return userkey;
	}



	public void setUserkey(String userKey) {
		this.userkey = userKey;
	}



	@OneToMany(cascade = CascadeType.ALL,mappedBy="Owner") 
	private List<GoogleAccount> googleAccounts; 
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy="owner") 
	private List<MicrosoftAccount> microsoftAccounts; 

	
	
	public List<GoogleAccount> getGoogleAccounts() {
		if(googleAccounts == null) {
			googleAccounts = new ArrayList<GoogleAccount>();
			
			return googleAccounts;
		}else {
			return googleAccounts;
		}
		
	}



	public void setGoogleAccounts(List<GoogleAccount> googleAccounts) {
		this.googleAccounts = googleAccounts;
	}



	public void addGoogleAccount(GoogleAccount account){
		
	    account.setOwner(this);
	    this.getGoogleAccounts().add(account);
	}
	public List<MicrosoftAccount> getMicrosoftAccounts() {
		if(microsoftAccounts == null) {
			microsoftAccounts = new ArrayList<MicrosoftAccount>();
			
			return microsoftAccounts;
		}else {
			return microsoftAccounts;
		}
		
    }

    /**
     * @param val the Microsoft account to set
     */
    public void setMicrosoftAccounts(final List<MicrosoftAccount> val) {
        this.microsoftAccounts = val;
    }

    /**
     * Add new Microsoft account to current user.
     * @param account Microsoft account to add
     */
    public void addMicrosoftAccount(final MicrosoftAccount account) {
        account.setOwner(this);
        this.microsoftAccounts.add(account);
    }



	
}
