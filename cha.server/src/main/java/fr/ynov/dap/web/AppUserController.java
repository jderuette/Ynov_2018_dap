package fr.ynov.dap.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.service.AppUserService;

@RestController
public class AppUserController {
	@Autowired
	AppUserService appUserService;
	
	@RequestMapping(value="/user/add/{userKey}")
	public Map<String, Object> addUserKey(@RequestParam("userKey") final String userId){
		return appUserService.addAppUser(userId);
	}
	
}
