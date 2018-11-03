package dap.client.service;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.ObjectMapper;

import dap.client.model.dto.AccountResponse;

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
     * @param userId the user to check your authorization
     *
     * @return an accountResponse
     *
     * @throws IOException if the mapping fall
     **/
    public AccountResponse connexionGoogleAccount(final String userId) throws IOException {
        InputStream inputStream = send("/account/add/" + userId, "GET");

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(inputStream, AccountResponse.class);
    }

    @Override
    final String getControllerRootURL() {

        return "";
    }

}
