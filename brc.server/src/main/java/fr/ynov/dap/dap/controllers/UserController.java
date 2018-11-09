package fr.ynov.dap.dap.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepostory;

@Controller
public class UserController {

	@Autowired
	public AppUserRepostory appUserRepostory;

	@RequestMapping("/user/add")
	public String addUserkey(@RequestParam final String userKey) {
		AppUser userToSave = new AppUser();
		userToSave.setUserKey(userKey);
	
		appUserRepostory.save(userToSave);
		
		return "succes";
	}
}
