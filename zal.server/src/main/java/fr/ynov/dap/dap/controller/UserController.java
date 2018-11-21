package fr.ynov.dap.dap.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.data.GoogleAccount;

@Controller
@RequestMapping("/user")
public class UserController{
	
	@Autowired 
	AppUserRepository repository;
	
	@RequestMapping("/add")
	public String addUser(@RequestParam final String userKey){
		GoogleAccount googleAccount = new GoogleAccount();
		AppUser user = new AppUser();
		user.setUserKey(userKey);
		
		repository.save(user);
		return "toto";
	}
}
