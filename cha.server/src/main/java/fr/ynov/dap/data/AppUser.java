package fr.ynov.dap.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import fr.ynov.dap.data.microsoft.MicrosoftAccount;

@Entity
public class AppUser {

	@Id
	@GeneratedValue
	public int id;
	
	@Column
	public String userKey;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="owner")
	private List<GoogleAccount> googleAccounts;
	
	 /**
     * List of every microsoft account for this user.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<MicrosoftAccount> microsoftAccounts;
	
	
	public int getId() {
		return id;
	}

	public String getUserKey() {
		return userKey;
	}

	public List<GoogleAccount> getGoogleAccounts() {
		return googleAccounts;
	}
	
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public void setGoogleAccounts(List<GoogleAccount> googleAccounts) {
		this.googleAccounts = googleAccounts;
	}
	
	public void addGoogleAccount(GoogleAccount account){
		
		account.setOwner(this);
		
		this.getGoogleAccounts().add(account);
		
	}
	
	/**
     * @return the MicrosoftAccount
     */
    public List<MicrosoftAccount> getMicrosoftAccounts() {
        return microsoftAccounts;
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
