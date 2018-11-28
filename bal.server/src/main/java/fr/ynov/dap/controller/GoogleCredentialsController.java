package fr.ynov.dap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/credentials")
public class GoogleCredentialsController  {

	/*
	 * @Autowired private CredentialsService credentialsService;
	 * 
	 * @RequestMapping("/") public String getCredentials(ModelMap model) throws
	 * GeneralSecurityException, IOException {
	 * 
	 * DataStore<StoredCredential> credentials =
	 * credentialsService.getCredentials(); HashMap<String, String> credentialsMap =
	 * new HashMap<>();
	 * 
	 * for (String k : credentials.keySet()) { credentialsMap.put(k,
	 * credentials.get(k).getAccessToken()); }
	 * model.addAttribute("credentialsMap",credentialsMap);
	 * 
	 * return "Credentials"; }
	 */
}
