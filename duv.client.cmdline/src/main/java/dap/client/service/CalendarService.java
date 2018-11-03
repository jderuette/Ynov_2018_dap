package dap.client.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import dap.client.model.dto.EventResponse;

/**
 * service related to userId's calendar.
 *
 * @author David_tepoche
 *
 */
public class CalendarService extends HttpClient {

    /**
     * get events from calendare of the userID account.
     *
     * @param userId         the user to check your authorization
     * @param nbrOfNextEvent the number of next event your want
     * @return list of EventResponse
     * @throws IOException throws because of the mapping
     */
    public List<EventResponse> getNextEvent(final String userId, final Integer nbrOfNextEvent) throws IOException {
        InputStream inputStream = send("/getNextEvent/" + nbrOfNextEvent + "/" + userId, "GET");

        ObjectMapper mapper = new ObjectMapper();
        return Arrays.asList(mapper.readValue(inputStream, EventResponse[].class));
    }

    @Override
    final String getControllerRootURL() {
        return "calendar";
    }

}
