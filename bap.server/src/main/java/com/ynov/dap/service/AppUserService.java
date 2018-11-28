package com.ynov.dap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynov.dap.domain.AppUser;
import com.ynov.dap.repository.AppUserRepository;

@Service
public class AppUserService extends BaseService {
	
    @Autowired
    private AppUserRepository appUserRepository;

	public void createUser(String userKey) {
    	AppUser appUser = new AppUser();
    	appUser.setName(userKey);
    	
    	AppUser userAdded = appUserRepository.save(appUser);
    	
    	if (userAdded != null) {
    		getLogger().info("User '" + userKey + "' added to appUser");
    	} else {
    		getLogger().error("Error while insert '" + userKey + "' to appUser");
    	}
	}

	@Override
	protected String getClassName() {
		return AppUserService.class.getName();
	}
	
}
