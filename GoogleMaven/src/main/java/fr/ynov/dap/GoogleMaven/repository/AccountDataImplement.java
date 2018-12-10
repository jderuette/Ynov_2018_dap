package fr.ynov.dap.GoogleMaven.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.*;
import javassist.NotFoundException;
import fr.ynov.dap.GoogleMaven.data.AppUser;
import fr.ynov.dap.GoogleMaven.data.GoogleAccount;

@Service
//TODO elj by Djer |POO| En général on suffixe par "Impl". Idéalement on précise la particularité de cette implementation (ici tu utilise JPA et des repository qui pourraient etre utilisé comme suffixe)
public class AccountDataImplement implements AccountData{
	
	@Autowired
	private GoogleAccountRepository googleAccountRepository;
	@Autowired
	private AppUserRepostory appUserRepository;
	
	
	public AppUser consulterUser(String userKey) throws NotFoundException {
	    for(AppUser cp1:appUserRepository.findAll()) 
	    {if(cp1.getUserKey().equals(userKey))
	     return cp1;
	    
	       }

	    //TODO elj by Djer |Gestion Exception| Il faut utiliser le mot clef "throw" si tu veux que ton exception soit levée. ici tu créé juste une "instance de classe" sans en conserver la référence
	   new NotFoundException("Compte introvable  ");
	   return null; 
	}
    
	
	public void addUser(String userKey) {
		 appUserRepository.save(new AppUser(userKey));
	}

	public void deleteUser(String userKey) {
	    //TODO elj by Djer |IDE| tyraite les TO-DO. A la limite leve une Exeption "NotImplemented" en attendant de coder cette méthode
		// TODO Auto-generated method stub
		
	}

	public List<GoogleAccount> listGoogleAccount1(String userKey) {
		 return googleAccountRepository.listGoogleAccount1(userKey);
	}

	public void ajouterAccount(String userkey, String adrMail,
			String accountName) throws NotFoundException {
	  //TODO elj by Djer |IDE| tyraite les TO-DO. A la limite leve une Exeption "NotImplemented" en attendant de coder cette méthode
		// TODO Auto-generated method stub
		
	}
    
	
	public String getAccount(int id) {
		  for(GoogleAccount listGoogleAccount:googleAccountRepository.findAll()) 
		    {if(listGoogleAccount.getId()==id)
		     return listGoogleAccount.getAccountName();
		     
		       }
		//TODO elj by Djer |Gestion Exception| Il faut utiliser le mot clef "throw" si tu veux que ton exception soit levée.
		   new NotFoundException("Compte introvable  ");
		   return null;  
	}

}
