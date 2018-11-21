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
import fr.ynov.dap.exception.NoGoogleAccountException;

/**
 * Class to manage Contact API.
 * @author Kévin Sibué
 *
 */
@Service
public class ContactService extends GoogleAPIService<PeopleService> {

    @Override
    protected final PeopleService getGoogleClient(final NetHttpTransport httpTransport, final Credential cdt,
            final String appName) {
        return new PeopleService.Builder(httpTransport, getJsonFactory(), cdt).setApplicationName(appName).build();
    }

    /**
     * Get next user's event.
     * @param accountName Current user id
     * @return User's number of contacts linked by userId
     * @throws IOException Exception
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     */
    private Integer getNumberOfContacts(final String accountName) throws GeneralSecurityException, IOException {

        PeopleService peopleSrv = getService(accountName);

        ListConnectionsResponse response = peopleSrv.people().connections().list("people/me").setPersonFields("names")
                .execute();

        Integer totalPeople = response.getTotalPeople();

        if (totalPeople != null) {

            return totalPeople;

        }

        return 0;

    }

    public Integer getNumberOfContacts(AppUser user)
            throws NoGoogleAccountException, GeneralSecurityException, IOException {

        if (user.getGoogleAccounts().size() == 0) {
            throw new NoGoogleAccountException();
        }

        Integer nbContacts = 0;

        for (GoogleAccount gAcc : user.getGoogleAccounts()) {
            String accountName = gAcc.getAccountName();
            nbContacts += getNumberOfContacts(accountName);
        }

        return nbContacts;

    }

    @Override
    protected final String getClassName() {
        return ContactService.class.getName();
    }

}
