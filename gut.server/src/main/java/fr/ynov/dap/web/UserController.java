package fr.ynov.dap.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.repository.AppUserRepository;

@Controller
public class UserController extends BaseController {

  //TODO gut by Djer |POO| SI tu ne précise pas, cette attribut aurat la même porté que la classe (donc public).
	@Autowired
	AppUserRepository appUserRepository;

	@RequestMapping("/user/add/{userKey}")
	public String addUser(ModelMap model, @PathVariable final String userKey) {
		getLogger().debug("UserController/addUser : Ajout d'un utilisateur");

		if (userKey != null) {
			if (appUserRepository.findByUserKey(userKey) == null) {
				appUserRepository.save(new AppUser(userKey));
				model.addAttribute("callbackMsg", "New account");
			} else {
				model.addAttribute("callbackMsg", "Account already added");
			}
		} else {
			model.addAttribute("callbackMsg", "Account name missing");
		}

		return "welcome";
	}
}
