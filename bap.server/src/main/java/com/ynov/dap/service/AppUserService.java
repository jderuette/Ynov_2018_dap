package com.ynov.dap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynov.dap.domain.AppUser;
import com.ynov.dap.repository.AppUserRepository;

@Service
public class AppUserService {
	
    @Autowired
    private AppUserRepository appUserRepository;

	public void createUser(String userKey) {
    	AppUser appUser = new AppUser();
    	appUser.setName(userKey);
    	
    	appUserRepository.save(appUser);
	}
	
}
