package fr.ynov.dap.client.service;

import java.io.IOException;

import fr.ynov.dap.client.dto.in.NumberContactInDto;
import fr.ynov.dap.client.exception.ServerSideException;

/**
 * This class allow client to discuss with server side to get user's contact information.
 * @author Kévin Sibué
 *
 */
public class ContactService extends HttpService {

    /**
     * Wrapper to retrieve user's number of contacts.
     * @param userId Current user id to retrieve user's credential
     * @return NumberContactInDto instance to describe number of contacts
     * @throws IOException Exception
     * @throws ServerSideException Exception thrown when server send an error
     */
    public NumberContactInDto getNumberOfContact(final String userId) throws IOException, ServerSideException {

        String url = getUrl() + "/nbContacts/" + userId;
        String response = sendGetRequest(url);

        return NumberContactInDto.fromJSON(response);

    }

    @Override
    protected final String getUrl() {
        return super.getUrl() + "/contact";
    }

}
