package fr.ynov.dap.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.interfaces.AppUserRepository;
import fr.ynov.dap.utils.ExtendsUtils;

@Controller
public class Welcome extends ExtendsUtils {
	
	@Autowired
	AppUserRepository appUserRepo;
	
	@RequestMapping("/")
	public String welcome(ModelMap model, @RequestParam(value="userKey", required=false) String user) {		
		if (user == null || user.length() == 0) {
			return "pleaselogin";
		}
		
		AppUser currentUser = appUserRepo.findByUserKey(user);
		
		LOG.info("login ok with : " + currentUser.getUserKey());

		model.addAttribute("userKey", "?userKey=" + user);
		model.addAttribute("googleAccount", currentUser.getGoogleAccounts());
		model.addAttribute("microsoftAccount", currentUser.getMicrosoftAccounts());
		
		return "welcome";
	}
}
