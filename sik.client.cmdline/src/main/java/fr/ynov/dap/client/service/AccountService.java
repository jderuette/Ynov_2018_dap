package fr.ynov.dap.client.service;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import fr.ynov.dap.client.exception.ServerSideException;

/**
 * This class allow client to discuss with server side to get user's account information.
 * @author Kévin Sibué
 *
 */
public class AccountService extends HttpService {

    /**
     * Allow client to create a new user.
     * @param userKey User's key
     * @throws ServerSideException Exception
     * @throws IOException Exception
     */
    public void createAccount(final String userKey) throws IOException, ServerSideException {

        String url = getUrl() + "/add/" + userKey;

        sendPostRequest(url);

    }

    /**
     * Add new google account to server.
     * @param userId Current user id
     * @param accountName Current user account name
     * @throws IOException Exception
     * @throws ServerSideException Exception thrown when server send an error
     * @throws URISyntaxException Exception thrown when uri is invalid
     */
    public void addGoogleAccount(final String accountName, final String userId)
            throws IOException, ServerSideException, URISyntaxException {

        String url = getUrl() + "/google/add/" + accountName + "/" + userId;

        URI redirectUrl = new URI(url);

        Desktop.getDesktop().browse(redirectUrl);

    }

    /**
     * Add new microsoft account to server.
     * @param userId Current user id
     * @param accountName Current user account name
     * @throws IOException Exception
     * @throws ServerSideException Exception thrown when server send an error
     * @throws URISyntaxException Exception thrown when uri is invalid
     */
    public void addMicrosoftAccount(final String accountName, final String userId)
            throws IOException, ServerSideException, URISyntaxException {

        String url = getUrl() + "/microsoft/add/" + accountName + "/" + userId;

        URI redirectUrl = new URI(url);

        Desktop.getDesktop().browse(redirectUrl);

    }

    @Override
    protected final String getUrl() {
        return super.getUrl() + "/account";
    }

}
