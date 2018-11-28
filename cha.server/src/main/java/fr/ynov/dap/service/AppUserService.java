package fr.ynov.dap.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.repository.AppUserRepository;

@Service
public class AppUserService {

	@Autowired
	private AppUserRepository appUserRepo;
	
	
	public Map<String, Object> addAppUser(String userKey) {		
		Map<String, Object> map = new HashMap<>();
		
		if(appUserRepo.findByUserKey(userKey) == null) {
			AppUser appUser = new AppUser();
			appUser.setUserKey(userKey);
			appUserRepo.save(appUser);
			
			
			map.put("success", true);
			map.put("Message", "Le compte a bien ete crée");
		}
		else {
			map.put("Sucess", false);
			map.put("Message", "Un utilisateur de se nom existe déjà");
		}
		
		return map;
	}
	
}
