package com.ynov.dap.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynov.dap.service.AppUserService;

@Controller
public class UserController {

    @Autowired
    private AppUserService appUserService;
	
    @RequestMapping("/user/add/{userKey}")
    public String addAccount(@PathVariable final String userKey, final HttpServletRequest request) {
    	
    	appUserService.createUser(userKey);
    	
        return "index";
    }

}
