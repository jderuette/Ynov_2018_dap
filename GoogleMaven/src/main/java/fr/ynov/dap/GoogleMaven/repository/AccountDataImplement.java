package fr.ynov.dap.GoogleMaven.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.*;
import javassist.NotFoundException;
import fr.ynov.dap.GoogleMaven.data.AppUser;
import fr.ynov.dap.GoogleMaven.data.GoogleAccount;

@Service
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

	   new NotFoundException("Compte introvable  ");
	   return null; 
	}
    
	
	public void addUser(String userKey) {
		 appUserRepository.save(new AppUser(userKey));
	}

	public void deleteUser(String userKey) {
		// TODO Auto-generated method stub
		
	}

	public List<GoogleAccount> listGoogleAccount1(String userKey) {
		 return googleAccountRepository.listGoogleAccount1(userKey);
	}

	public void ajouterAccount(String userkey, String adrMail,
			String accountName) throws NotFoundException {
		// TODO Auto-generated method stub
		
	}
    
	
	public String getAccount(int id) {
		  for(GoogleAccount listGoogleAccount:googleAccountRepository.findAll()) 
		    {if(listGoogleAccount.getId()==id)
		     return listGoogleAccount.getAccountName();
		     
		       }

		   new NotFoundException("Compte introvable  ");
		   return null;  
	}

}
