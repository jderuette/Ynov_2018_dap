package com.ynov.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynov.dap.service.AdminService;

@RequestMapping("admin")
@Controller
public class AdminController extends BaseController {

    @Autowired
    private AdminService adminService;
	
	@GetMapping("/google")
	public String returnGoogleDataStore(final ModelMap model) throws GeneralSecurityException, IOException {
		model.addAttribute("dataStore", adminService.getGoogleDataStore());

		return "admin/data_google";
	}
	
	@GetMapping("/microsoft")
	public String returnMicrosoftToken(final ModelMap model) {
		model.addAttribute("tokens", adminService.getMicrosoftDataStore());

		return "admin/data_microsoft";
	}
	
	@GetMapping("/")
	public String returnDataStoreAndToken(final ModelMap model) throws GeneralSecurityException, IOException {
		model.addAttribute("dataStore", adminService.getGoogleDataStore());
		model.addAttribute("tokens", adminService.getMicrosoftDataStore());

		return "admin/data";
	}
	
	@Override
	public String getClassName() {
		return AdminController.class.getName();
	}
}
