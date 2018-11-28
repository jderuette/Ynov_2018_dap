package fr.ynov.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ListConnectionsResponse;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.GoogleAccount;

/**
 * The Class PeopleAPIService.
 */
@Service
public class PeopleAPIService extends GoogleService<PeopleService> {
	
	/* (non-Javadoc)
	 * @see fr.ynov.dap.google.GoogleService#getClassName()
	 */
	@Override
    protected final String getClassName() {
        return PeopleAPIService.class.getName();
}
	
	/* (non-Javadoc)
	 * @see fr.ynov.dap.google.GoogleService#getGoogleClient(com.google.api.client.auth.oauth2.Credential, com.google.api.client.http.javanet.NetHttpTransport, java.lang.String)
	 */
	@Override
    protected final PeopleService getGoogleClient(final Credential cdt, final NetHttpTransport httpTransport, 
            final String appName) {
        return new PeopleService.Builder(httpTransport, getJSON_FACTORY(), cdt).setApplicationName(appName).build();
	}


    /**
     * Gets the total people.
     *
     * @param userKey the user key
     * @return the total people
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws GeneralSecurityException the general security exception
     */
    public int getTotalPeople(String userKey) throws IOException, GeneralSecurityException {
        
    	PeopleService service = getService(userKey);
        ListConnectionsResponse response = service.people().connections()
                .list("people/me")
                .setPageSize(10)
                .setPersonFields("names,emailAddresses")
                .execute();
       int totalPeople = response.getTotalPeople(); 
       return totalPeople;
    }
    
    /**
     * Gets the total people all account.
     *
     * @param user the user
     * @return the total people all account
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws GeneralSecurityException the general security exception
     */
    public int getTotalPeopleAllAccount(final AppUser user) throws IOException, GeneralSecurityException {
		int nbPeopleAllAccount = 0;
		if(user != null) {
			for (GoogleAccount currentData : user.getGoogleAccounts()) {
				nbPeopleAllAccount += getTotalPeople(currentData.getAccountName());
	        }
		}
		return nbPeopleAllAccount;
	}
}