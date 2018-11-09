package fr.ynov.dap.dap.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;

import fr.ynov.dap.dap.GoogleService;
import fr.ynov.dap.dap.models.GmailResponse;

@Controller
public class GoogleController {
	
	@Autowired 
	@Qualifier("google")
	GoogleService googleService;
	
	@RequestMapping("/datastore")
	public String googleCredential(ModelMap model) throws IOException, Exception {
		DataStore<StoredCredential> output = googleService.getCredentialDataStore();
		Map<Integer, String> map= new HashMap<Integer, String>();
		
		int count = 0;
		for(StoredCredential sc : output.values()){
			map.put(count, sc.getAccessToken());
			count++;
		}
		
		model.addAttribute("credentials",map);
		
		return "datastore";
	}

}
