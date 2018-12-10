package fr.ynov.dap.GoogleMaven.data;

//TODO elj by Djer |IDE| Configure les "save action" de ton IDE qu'il néttoie les import (et format ton code) quand tu sauveagrdes pour éviter  ce genre d'oublie
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
//TODO elj by Djer |API Microsoft| Tu n'utilise pas cette entité ? (devrait être en "one to many dans AppUser + Repository + utiliser dans quelques services Microsoft)
//TODO elj by Djer |POO| Attention tu as déja une classe avec le meme nom dans le package "data", ca n'est pas nécéssairement bloquant mais au mieux ambigue
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
	    //TODO elj by Djer |JPA| Finie cette implémentation !
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
