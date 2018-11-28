package fr.ynov.dap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ynov.dap.model.AppUserModel;
import fr.ynov.dap.repository.AppUserRepository;

@Service
public class AppUserService {

	@Autowired
	AppUserRepository appUserRepository;
	
	public String addUser(String userKey) {
		AppUserModel newUser = new AppUserModel();
		newUser.setUserKey(userKey);
		appUserRepository.save(newUser);
		return "redirect:/";
	}
	
}
