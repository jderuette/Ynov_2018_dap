package fr.ynov.dap.client.service;

import java.io.IOException;

import fr.ynov.dap.client.dto.in.LoginResponseInDto;
import fr.ynov.dap.client.exception.ServerSideException;

/**
 * This class allow client to discuss with server side to get user's account information.
 * @author Kévin Sibué
 *
 */
public class AccountService extends HttpService {

    /**
     * Add new google account to server.
     * @param userId Current user id
     * @return LoginResponseInDto instance from server
     * @throws IOException Exception
     * @throws ServerSideException Exception thrown when server send an error
     */
    public LoginResponseInDto addAccount(final String userId) throws IOException, ServerSideException {

        String url = getUrl() + "/add/" + userId;
        String response = sendPostRequest(url);

        LoginResponseInDto dto = LoginResponseInDto.fromJSON(response);

        return dto;

    }

    @Override
    protected final String getUrl() {
        return super.getUrl() + "/account";
    }

}
