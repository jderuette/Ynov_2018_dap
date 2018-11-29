package fr.ynov.dap.GoogleMaven.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class GoogleAccount {


	@Id
	@GeneratedValue
	int id;
	
	
	@ManyToOne
	AppUser owner;
	String type; 
    String accountName;
    String EmailAdr;
    
    public GoogleAccount(){}
    
    public GoogleAccount(AppUser owner ,String accountName, String EmailAdr,String type){
    	this.owner = owner;
    	this.accountName = accountName;
    	this.EmailAdr = EmailAdr;
    	this.type = "Google";
    }
    
	public void setOwner(AppUser owner) {
		// TODO Auto-generated method stub
		
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public int getId() {
		return id;
	}

	public AppUser getOwner() {
		return owner;
	}
	
	
	
}
