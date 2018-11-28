package com.ynov.dap.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynov.dap.service.AppUserService;

/**
 * The Class UserController.
 */
@Controller
public class UserController extends BaseController {

    /** The app user service. */
    @Autowired
    private AppUserService appUserService;
	
    /**
     * Adds the account.
     *
     * @param userKey the user key
     * @param request the request
     * @return the string
     */
    @RequestMapping("/user/add/{userKey}")
    public String addAccount(@PathVariable final String userKey, final HttpServletRequest request) {
    	
    	appUserService.createUser(userKey);
    	
        return "index";
    }
    
	/* (non-Javadoc)
	 * @see com.ynov.dap.controller.BaseController#getClassName()
	 */
	@Override
	public String getClassName() {
		return UserController.class.getName();
	}

}
