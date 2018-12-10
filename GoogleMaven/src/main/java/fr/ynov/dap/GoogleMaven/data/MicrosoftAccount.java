package fr.ynov.dap.GoogleMaven.data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class MicrosoftAccount {
	@Id
	@GeneratedValue
	int id;
	
	
	@ManyToOne
	AppUser owner;

    String accountName;
    String EmailAdr;
    String type;
    public MicrosoftAccount(){}
    
    public MicrosoftAccount(AppUser owner ,String accountName, String EmailAdr){
    	this.owner = owner;
    	this.accountName = accountName;
    	this.EmailAdr = EmailAdr;
    	this.type= "Microsoft";
    }
    
	public void setOwner(AppUser owner) {
		// TODO Auto-generated method stub
		
	}
	
	public String getType(){
		return type;
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
