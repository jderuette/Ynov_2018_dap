package fr.ynov.dap.controllers;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;

import fr.ynov.dap.google.service.GoogleService;
import fr.ynov.dap.microsoft.data.MicrosoftAccount;
import fr.ynov.dap.microsoft.data.MicrosoftAccountRepository;
import fr.ynov.dap.models.AdminModel;

@Controller
public class AdminController {
	
	@Autowired 
	@Qualifier("google")
	GoogleService googleService;
	
	@Autowired
	MicrosoftAccountRepository microsoftAccountRepository;
	
	@RequestMapping("/admin")
	public String googleCredential(ModelMap model) throws IOException, Exception {
		ArrayList<AdminModel> accountsCredential = new ArrayList<AdminModel>();

		//Add Google Credentials from storedCredentials
		DataStore<StoredCredential> googleCredentials = googleService.getCredentialDataStore();
		for(String key : googleCredentials.keySet()){
			StoredCredential sc = googleCredentials.get(key);
			AdminModel am = new AdminModel();
			am.setAccessToken(sc.getAccessToken());
			am.setName(key);
			am.setTenantId("");
			am.setType("Google");
			accountsCredential.add(am);
		}
		
		//Add Microsoft Credentials from dataBase
		Iterable<MicrosoftAccount> msCredentials = microsoftAccountRepository.findAll();
		for(MicrosoftAccount msAccount : msCredentials){
			AdminModel am = new AdminModel();
			am.setAccessToken(msAccount.getToken().getAccessToken());
			am.setName(msAccount.getName());
			am.setTenantId(msAccount.getTenantId());
			am.setType("microsoft");
			accountsCredential.add(am);
		}
		
		model.addAttribute("credentials",accountsCredential);
		
		return "admin";
	}

}
