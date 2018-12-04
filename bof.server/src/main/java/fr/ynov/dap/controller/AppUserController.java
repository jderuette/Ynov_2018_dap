package fr.ynov.dap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.service.AppUserService;

@Controller
public class AppUserController {

	@Autowired
	AppUserService appUserService;
	
	//TODO bof by Djer |IDE| Configure les "save actions" de ton IDE pour formater le code à la sauvegarde, cela évitera ce petit "double espace" entre final et le type.
	@RequestMapping(value="/user/add/{userKey}")
	public String addUser(@PathVariable final  String userKey) {
		return appUserService.addUser(userKey);
	}
	
}
