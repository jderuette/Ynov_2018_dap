package fr.ynov.dap.controllers;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
//TODO brc by Djer |IDE| Configure les "save action" de ton IDE pour qu'il organise les import (et formate ton code) lors de la sauvegarde
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;

import fr.ynov.dap.google.service.GoogleService;
import fr.ynov.dap.microsoft.data.MicrosoftAccount;
import fr.ynov.dap.microsoft.data.MicrosoftAccountRepository;
import fr.ynov.dap.models.AdminModel;
import fr.ynov.dap.service.AdminService;

/**
 * The Class AdminController.
 */
@Controller
public class AdminController {
	
	/** The admin service. */
  //TODO brc by Djer |POO| Il faut préciser le modifier (public/protected/private) sur tes attributs, sinon par defaut c'est celui de la classe (donc public ici)
	@Autowired 
	AdminService adminService;
	
	/**
	 * Google credential.
	 *
	 * @param model the model
	 * @param userKey the user key
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	@RequestMapping("/admin")
	public String googleCredential(ModelMap model, @RequestParam final String userKey) throws IOException, Exception {
		ArrayList<AdminModel> accountsCredential = new ArrayList<AdminModel>();

		//Add Google Credentials from storedCredentials
		accountsCredential.addAll(adminService.getGoogleCredentials());
		
		//Add Microsoft Credentials from dap database
		accountsCredential.addAll(adminService.getMsCredentials());
		
		model.addAttribute("userKey", userKey);
		model.addAttribute("credentials",accountsCredential);
		
		return "admin";
	}

}
