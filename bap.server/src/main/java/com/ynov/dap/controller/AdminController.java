package com.ynov.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynov.dap.service.AdminService;

/**
 * The Class AdminController.
 */
@RequestMapping("admin")
@Controller
public class AdminController extends BaseController {

    /** The admin service. */
    @Autowired
    private AdminService adminService;
	
	/**
	 * Return google data store.
	 *
	 * @param model the model
	 * @return the string
	 * @throws GeneralSecurityException the general security exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
    //TODO bap by Djer |POO| Toutes les méthodes "return" quelquechose (éventuellement "void") donc ne préfix pas tes méthodes par "return". Si tu souhaites éviter le "getXxxxxx" (qui serait un "faux getter") tu peux utiliser "load", "retrieve" ou "search" (suivant les cas)
	@GetMapping("/google")
	public String returnGoogleDataStore(final ModelMap model) throws GeneralSecurityException, IOException {
		model.addAttribute("dataStore", adminService.getGoogleDataStore());

		return "admin/data_google";
	}
	
	/**
	 * Return microsoft token.
	 *
	 * @param model the model
	 * @return the string
	 */
	@GetMapping("/microsoft")
	public String returnMicrosoftToken(final ModelMap model) {
		model.addAttribute("tokens", adminService.getMicrosoftDataStore());

		return "admin/data_microsoft";
	}
	
	/**
	 * Return data store and token.
	 *
	 * @param model the model
	 * @return the string
	 * @throws GeneralSecurityException the general security exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@GetMapping("/")
	public String returnDataStoreAndToken(final ModelMap model) throws GeneralSecurityException, IOException {
		model.addAttribute("dataStore", adminService.getGoogleDataStore());
		model.addAttribute("tokens", adminService.getMicrosoftDataStore());

		return "admin/data";
	}
	
	/* (non-Javadoc)
	 * @see com.ynov.dap.controller.BaseController#getClassName()
	 */
	@Override
	public String getClassName() {
		return AdminController.class.getName();
	}
}
