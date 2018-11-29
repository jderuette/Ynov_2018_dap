package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;

import fr.ynov.dap.service.GMailService;

@RestController
/**
 * Accède à l'URL relatives à la page d'accueil du site.
 * @author abaracas
 *
 */
public class BienvenuController {
    @Autowired GMailService gmailService;
    
	@RequestMapping("/{accountName}")
	/**
	 * Affiche les infos générales du compte.
	 * @param model Model
	 * @param accountName nom du compte google
	 * @return l'url où sont affichés les résultats
	 * @throws IOException exception
	 * @throws GeneralSecurityException exception
	 */
	public String Wilkomen(ModelMap model, @PathVariable final String accountName) throws IOException, GeneralSecurityException {
	    model.addAttribute("nbEmails", gmailService.getUnreadMessageCount(accountName));
	    return "Wilkomen";
	}
	
	@RequestMapping("/admin")
	/**
	 * Affiche les infos plus sensibles des comptes.
	 * @param model Model
	 * @return l'url où sont affichés les résultats 
	 * @throws IOException exception
	 * @throws GeneralSecurityException exception
	 */
	public String Credential(ModelMap model) throws IOException, GeneralSecurityException {
	    DataStore<StoredCredential> credentials = gmailService.getCredentialDatastore();
	    model.addAttribute("date", gmailService.getCredentialDatastore());
	    Map<String, DataStore<StoredCredential>> maMap = new HashMap<>();
	    maMap.put("data", gmailService.getCredentialDatastore());
	    for(String aaKey : credentials.keySet()) {
	            maMap.put(aaKey, maMap.get(aaKey));	        
	    }
	    model.addAttribute("data", maMap);
	    return "credential";
	}
}
