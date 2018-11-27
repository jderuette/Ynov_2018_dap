package com.ynov.dap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynov.dap.service.AdminService;

@RequestMapping("admin")
@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;
	
	@GetMapping("/google")
	public String returnGoogleDataStore(final ModelMap model) {
		model.addAttribute("dataStore", adminService.getGoogleDataStore());

		return "admin/data_google";
	}
	
	@GetMapping("/microsoft")
	public String returnMicrosoftToken(final ModelMap model) {
		model.addAttribute("dataStore", adminService.getMicrosoftDataStore());

		return "admin/data_microsoft";
	}
}
