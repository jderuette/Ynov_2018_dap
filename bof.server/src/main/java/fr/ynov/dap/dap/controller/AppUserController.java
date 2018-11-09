package fr.ynov.dap.dap.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.dap.model.AppUserModel;
import fr.ynov.dap.dap.repository.AppUserRepository;

@Controller
public class AppUserController {

	@Autowired
	AppUserRepository appUserRepository;
	
	@RequestMapping(value="/user/add/{userKey}")
	public String addUser(@PathVariable final  String userKey) {
		AppUserModel newUser = new AppUserModel();
		newUser.setUserKey(userKey);
		appUserRepository.save(newUser);
		return "redirect:/";
	}
	
}
