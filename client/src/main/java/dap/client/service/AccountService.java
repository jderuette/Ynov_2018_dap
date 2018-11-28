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
     * @param userKey     the user in BDD
     * @param accountName hte alias used to connect in google account
     *
     * @throws IOException        if the mapping fall
     * @throws URISyntaxException dunno
     **/
    public void connexionGoogleAccount(final String userKey, final String accountName)
            throws IOException, URISyntaxException {

        URI uri = new URI(Config.getConf().getRootUrl() + "/account/add/" + userKey + "/" + accountName);
        Desktop.getDesktop().browse(uri);
    }

    @Override
    final String getControllerRootURL() {

        return "";
    }

}
