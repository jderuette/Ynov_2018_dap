package fr.ynov.dap.dap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.repository.AppUserRepository;

@Controller
public class UserController {
	
	@Autowired
	public AppUserRepository repo;
	
	@RequestMapping("/user/add/{userKey}")
	public String addUser(@PathVariable String userKey)
	{
		AppUser user = new AppUser();
		user.setUserKey(userKey);
		repo.save(user);
		
		return "redirect:/";
	}
	
}
