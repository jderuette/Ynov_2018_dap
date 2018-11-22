package fr.ynov.dap.client.service;

import java.io.IOException;

import fr.ynov.dap.client.dto.in.NextEventInDto;
import fr.ynov.dap.client.dto.in.NumberContactInDto;
import fr.ynov.dap.client.dto.in.UnreadMailInDto;
import fr.ynov.dap.client.exception.ServerSideException;

/**
 * This class allow client to discuss with server side to get DaP information.
 * @author Kévin Sibué
 *
 */
public class DaPAPIService extends HttpService {

    /**
     * Wrapper to retrieve user's number of contacts.
     * @param userId Current user id to retrieve user's credential
     * @return NumberContactInDto instance to describe number of contacts
     * @throws IOException Exception
     * @throws ServerSideException Exception thrown when server send an error
     */
    public NumberContactInDto getNumberOfContact(final String userId) throws IOException, ServerSideException {

        String url = getUrl() + "/contact/nbContacts/" + userId;
        String response = sendGetRequest(url);

        return NumberContactInDto.fromJSON(response);

    }

    /**
     * Wrapper to retrieve user's next event.
     * @param userId Current user id to retrieve user's credential
     * @return NextEventInDto instance to describe next event
     * @throws IOException Exception
     * @throws ServerSideException Exception thrown when server send an error
     */
    public NextEventInDto getNextEvent(final String userId) throws IOException, ServerSideException {

        String url = getUrl() + "/calendar/nextEvent/" + userId;
        String response = sendGetRequest(url);

        NextEventInDto dto = NextEventInDto.fromJSON(response);

        return dto;

    }

    /**
     * Wrapper to retrieve user's number of unread mail.
     * @param userId Current user id to retrieve user's credential
     * @return UnreadMailInDto instance to describe nb of unread mail
     * @throws IOException Exception
     * @throws ServerSideException Exception thrown when server send an error
     */
    public UnreadMailInDto getUnreadMail(final String userId) throws IOException, ServerSideException {

        String url = getUrl() + "/mail/nbUnread/" + userId;
        String response = sendGetRequest(url);

        UnreadMailInDto dto = UnreadMailInDto.fromJSON(response);

        return dto;

    }

}
