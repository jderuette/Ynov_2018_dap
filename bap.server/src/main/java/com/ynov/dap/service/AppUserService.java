package com.ynov.dap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynov.dap.domain.AppUser;
import com.ynov.dap.repository.AppUserRepository;

/**
 * The Class AppUserService.
 */
@Service
public class AppUserService extends BaseService {
	
    /** The app user repository. */
    @Autowired
    private AppUserRepository appUserRepository;

	/**
	 * Creates the user.
	 *
	 * @param userKey the user key
	 */
	public void createUser(final String userKey) {
    	AppUser appUser = new AppUser();
    	appUser.setName(userKey);
    	
    	AppUser userAdded = appUserRepository.save(appUser);
    	
    	if (userAdded != null) {
    		getLogger().info("User '" + userKey + "' added to appUser");
    	} else {
    		getLogger().error("Error while insert '" + userKey + "' to appUser");
    	}
	}

	/* (non-Javadoc)
	 * @see com.ynov.dap.service.BaseService#getClassName()
	 */
	@Override
	protected String getClassName() {
		return AppUserService.class.getName();
	}
	
}
