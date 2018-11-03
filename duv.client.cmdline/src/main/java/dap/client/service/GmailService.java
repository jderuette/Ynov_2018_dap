package dap.client.service;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * service related to userId's emails.
 *
 * @author David_tepoche
 *
 */
public class GmailService extends HttpClient {

    @Override
    final String getControllerRootURL() {
        return "emails";
    }

    /**
     * get the number or unread email of the userId.
     *
     * @param userId the user to check your authorization
     * @return number of the unread email
     * @throws IOException throw if the mapping fail
     */
    public Integer getNbrEmailUnread(final String userId) throws IOException {
        InputStream inputStream = send("/nbrunreadmail/" + userId, "GET");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(inputStream, Integer.class);

    }
}
