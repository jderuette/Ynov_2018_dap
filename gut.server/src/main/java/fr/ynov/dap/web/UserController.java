package fr.ynov.dap.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.repository.AppUserRepository;

@Controller
public class UserController extends BaseController {
	
	@Autowired AppUserRepository appUserRepository;
	
	@RequestMapping("/user/add/{userKey}")
	public String addUser(@PathVariable final String userKey) {
		getLogger().debug("UserController/addUser : Ajout d'un utilisateur");
		appUserRepository.save(new AppUser(userKey));
		return "welcome";
	}
}
