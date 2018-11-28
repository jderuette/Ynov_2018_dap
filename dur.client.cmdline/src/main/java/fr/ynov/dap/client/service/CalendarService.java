package fr.ynov.dap.client.service;

import java.io.IOException;

import fr.ynov.dap.client.dto.in.NextEventInDto;
import fr.ynov.dap.client.exception.ServerSideException;

/**
 * This class allow client to discuss with server side to get user's calendar information.
 * @author Kévin Sibué
 *
 */
public class CalendarService extends HttpService {

    /**
     * Wrapper to retrieve user's next event.
     * @param userId Current user id to retrieve user's credential
     * @return NextEventInDto instance to describe next event
     * @throws IOException Exception
     * @throws ServerSideException Exception thrown when server send an error
     */
    public NextEventInDto getNextEvent(final String userId) throws IOException, ServerSideException {

        String url = getUrl() + "/nextEvent/" + userId;
        String response = sendGetRequest(url);

        NextEventInDto dto = NextEventInDto.fromJSON(response);

        return dto;

    }

    @Override
    protected final String getUrl() {
        return super.getUrl() + "/calendar";
    }

}
