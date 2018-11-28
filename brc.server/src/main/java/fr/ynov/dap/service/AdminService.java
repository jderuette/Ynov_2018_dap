package fr.ynov.dap.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;

import fr.ynov.dap.google.service.GoogleService;
import fr.ynov.dap.microsoft.data.MicrosoftAccount;
import fr.ynov.dap.microsoft.data.MicrosoftAccountRepository;
import fr.ynov.dap.microsoft.service.OutlookService;
import fr.ynov.dap.models.AdminModel;

@Service
public class AdminService {
	
	@Autowired 
	@Qualifier("google")
	GoogleService googleService;
	
	@Autowired
	MicrosoftAccountRepository microsoftAccountRepository;
	
	/** The logger. */
	private final static Logger logger = LogManager.getLogger(OutlookService.class);
	
	public ArrayList<AdminModel> getGoogleCredentials() throws IOException, GeneralSecurityException{
		logger.info("get google credentials started");
		
		ArrayList<AdminModel> accountsCredential = new ArrayList<AdminModel>();

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
		
		return accountsCredential;
	}
	
	public ArrayList<AdminModel> getMsCredentials(){
		logger.info("get Microsoft credentials started");

		ArrayList<AdminModel> accountsCredential = new ArrayList<AdminModel>();

		Iterable<MicrosoftAccount> msCredentials = microsoftAccountRepository.findAll();
		for(MicrosoftAccount msAccount : msCredentials){
			AdminModel am = new AdminModel();
			am.setAccessToken(msAccount.getToken().getAccessToken());
			am.setName(msAccount.getName());
			am.setTenantId(msAccount.getTenantId());
			am.setType("microsoft");
			accountsCredential.add(am);
		}
		
		return accountsCredential;
	}

}
