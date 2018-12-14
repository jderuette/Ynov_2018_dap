package fr.ynov.dap.dap.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import org.springframework.stereotype.Service;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;

import fr.ynov.dap.dap.data.microsoft.MicrosoftAccount;
import fr.ynov.dap.dap.data.microsoft.MicrosoftAccountRepository;
import fr.ynov.dap.dap.data.microsoft.TokenResponse;
import fr.ynov.dap.dap.model.CredentialModel;
import fr.ynov.dap.dap.service.google.GoogleService;
import fr.ynov.dap.dap.utils.Constants;

/**
 * The Class AdminService.
 */
@Service
public class AdminService extends GoogleService {

	/**
	 * Gets the credentials google data store.
	 *
	 * @return the credentials google data store
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException
	 *             the general security exception
	 */
	public ArrayList<CredentialModel> getCredentialsGoogleDataStore() throws IOException, GeneralSecurityException {
		DataStore<StoredCredential> dataStore = this.getFlow().getCredentialDataStore();
		ArrayList<CredentialModel> googleCredentials = new ArrayList<CredentialModel>();

		for (String key : dataStore.keySet()) {
			StoredCredential storedCredential = dataStore.get(key);
			CredentialModel credentialModel = new CredentialModel(key, storedCredential.getAccessToken(),
					Constants.GOOGLE_TYPE_CREDENTIAL, "", storedCredential.getRefreshToken());
			googleCredentials.add(credentialModel);
		}

		return googleCredentials;
	}

	/**
	 * Gets the credentials microsoft.
	 *
	 * @param repository
	 *            the repository
	 * @return the credentials microsoft
	 */
	public ArrayList<CredentialModel> getCredentialsMicrosoft(MicrosoftAccountRepository repository) {
		ArrayList<CredentialModel> listCredential = new ArrayList<CredentialModel>();
		//TODO zal by Djer |POO| Tu peux conserver le "Iterable<T>". A la limite Cast en "List" mais pas en ArrayList (tu n'est absolument pas certains que l'implementation de "findAll" renvoie (et continura à renvoyer) une **Array**List
		ArrayList<MicrosoftAccount> listMsAccount = (ArrayList<MicrosoftAccount>) repository.findAll();

		for (MicrosoftAccount account : listMsAccount) {
			TokenResponse token = account.getToken();
			CredentialModel msCredential = new CredentialModel(account.getEmail(), token.getAccessToken(),
					Constants.MICROSOFT_TYPE_CREDENTIAL, account.getTenantId(), token.getRefreshToken());
			listCredential.add(msCredential);
		}

		return listCredential;
	}

	/**
	 * Gets the all credential list.
	 *
	 * @param googleCredentials
	 *            the google credentials
	 * @param MicrosoftCredentials
	 *            the microsoft credentials
	 * @return the all credential list
	 */
	//TODO zal by Djer |POO| Renvoie une List (interface) plutot qu'un Arraylist (implementation), voir une "Collection" ou un Iterable
	public ArrayList<CredentialModel> getAllCredentialList(ArrayList<CredentialModel> googleCredentials,
			ArrayList<CredentialModel> MicrosoftCredentials) {

		ArrayList<CredentialModel> allCredentialList = new ArrayList<CredentialModel>();
		allCredentialList.addAll(googleCredentials);
		allCredentialList.addAll(MicrosoftCredentials);
		Collections.shuffle(allCredentialList);

		return allCredentialList;

	}
}
