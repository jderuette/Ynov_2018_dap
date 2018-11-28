package dap.client.service;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import dap.client.Config;

/**
 * service related to google account / authorization.
 *
 * @author David_tepoche
 *
 */
public class AccountService extends HttpClient {

    /**
     * intitiat the connection , to get if you are authorized or ask you to make
     * them.
     *
     * @param typeAccount type of account ( microsoft or google)
     * @param userKey     the user in BDD
     * @param accountName hte alias used to connect in google account
     *
     * @throws IOException        if the mapping fall
     * @throws URISyntaxException dunno
     **/
    public void connexionGoogleAccount(final String userKey, final String accountName, final String typeAccount)
            throws IOException, URISyntaxException {

        final URI uri = new URI(
                Config.getConf().getRootUrl() + "/account/add/" + userKey + "/" + accountName + "/" + typeAccount);
        Desktop.getDesktop().browse(uri);
    }

    @Override
    final String getControllerRootURL() {

        return "";
    }

}
