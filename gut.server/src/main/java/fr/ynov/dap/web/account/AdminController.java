package fr.ynov.dap.web.account;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.repository.AppUserRepository;
import fr.ynov.dap.repository.GoogleAccountRepository;
import fr.ynov.dap.repository.MicrosoftAccountRepository;

@Controller
public class AdminController {

	@Autowired
	AppUserRepository 
	appUserRepository;
	
	@Autowired 
	MicrosoftAccountRepository 
	microsoftAccountRepository;
	
	@Autowired
	GoogleAccountRepository
	googleAccountRepository;
	
	@RequestMapping("/admin")
	public String addUser(ModelMap model, @PathVariable final String userKey) {
		Iterable<AppUser> userList = appUserRepository.findAll();
		
		model.addAttribute(userList);
		
		return "admin";
	}
	
}
