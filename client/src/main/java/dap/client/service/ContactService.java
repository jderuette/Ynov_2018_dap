package dap.client.service;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * service related to userId's contact.
 *
 * @author David_tepoche
 *
 */
public class ContactService extends HttpClient {

    /**
     * get the total of userID's contact.
     *
     * @param userId the user to check your authorization
     * @return the nomber of contact
     * @throws IOException throw if the mapping fail
     */
    public Integer getNbrContact(final String userId) throws IOException {

        String value = send("/nbrContact/" + userId, "GET");

        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(value, Integer.class);

    }

    @Override
    final String getControllerRootURL() {
        return "contact";
    }

}
