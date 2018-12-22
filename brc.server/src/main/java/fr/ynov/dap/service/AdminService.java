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

/**
 * The Class AdminService.
 */
@Service
public class AdminService {
	
	/** The google service. */
  //TODO brc by Djer |POO| Il faut préciser le modifier (public/protected/private) sur tes attributs, sinon par defaut c'est celui de la classe (donc public ici)
	@Autowired
	//TODO brc by Djer |Spring| Pourquoi utiliser un qualifier. Le qualifier sert à Spring s'il y a des "doublons", par exemple si ton MicrosoftService et ton Google Service provennaient de la même classe, mais qu'il y a 2 instance différente et que tu souhaite les duistinguer (on a très rarement besoin des qualifier)
	@Qualifier("google")
	GoogleService googleService;
	
	/** The microsoft account repository. */
	//TODO brc by Djer |POO| Il faut préciser le modifier (public/protected/private) sur tes attributs, sinon par defaut c'est celui de la classe (donc public ici)
	@Autowired
	MicrosoftAccountRepository microsoftAccountRepository;
	
	/** The logger. */
	//TODO brc by Djer |POO| Devrait être écris en Majuscule (car static et final)
    //TODO brc by Djer |Audit Code| static devrait être avant final (PMD/Checkstyle te préviennent de cette inversion)
	private final static Logger logger = LogManager.getLogger(OutlookService.class);
	
	/**
	 * Gets the google credentials.
	 *
	 * @return the google credentials
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	//TODO brc by Djer |POO| Renvoie une List (interface) plutot qu'un ArrayList (implementation)
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
	
	/**
	 * Gets the ms credentials.
	 *
	 * @return the ms credentials
	 */
	//TODO brc by Djer |POO| Renvoie une List (interface) plutot qu'un ArrayList (implementation)
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
