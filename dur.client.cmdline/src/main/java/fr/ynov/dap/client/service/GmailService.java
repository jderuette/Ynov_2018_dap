package fr.ynov.dap.client.service;

import java.io.IOException;

import fr.ynov.dap.client.dto.in.UnreadMailInDto;
import fr.ynov.dap.client.exception.ServerSideException;

/**
 * This class allow client to discuss with server side to get user's mail information.
 * @author Kévin Sibué
 *
 */
public class GmailService extends HttpService {

    /**
     * Wrapper to retrieve user's number of unread mail.
     * @param userId Current user id to retrieve user's credential
     * @return UnreadMailInDto instance to describe nb of unread mail
     * @throws IOException Exception
     * @throws ServerSideException Exception thrown when server send an error
     */
    public UnreadMailInDto getUnreadMail(final String userId) throws IOException, ServerSideException {

        String url = getUrl() + "/nbUnread/" + userId;
        String response = sendGetRequest(url);

        UnreadMailInDto dto = UnreadMailInDto.fromJSON(response);

        return dto;

    }

    @Override
    protected final String getUrl() {
        return super.getUrl() + "/gmail";
    }

}
