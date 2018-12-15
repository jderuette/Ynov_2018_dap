package fr.ynov.dap.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.repository.AppUserRepository;

/**
 * The Class AppUserService.
 */
@Service
public class AppUserService {

	/** The app user repo. */
	@Autowired
	private AppUserRepository appUserRepo;
	
	
	/**
	 * Adds the app user.
	 *
	 * @param userKey the user key
	 * @return the map
	 */
	//TODO cha by Djer |MVC| Ressemble plus à une méthode de Controller qu'a un Service. Si service renvoie un objet "metier" (une instance de AppUser). SI controller, déplace dans le package "controlelr" (cette classe peut éventuellement être un "helper")
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
