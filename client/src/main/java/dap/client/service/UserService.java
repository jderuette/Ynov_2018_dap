package dap.client.service;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import dap.client.model.dto.UserResponse;

/**
 *
 * @author David_tepoche
 *
 */
public class UserService extends HttpClient {

    /**
     * create the user.
     *
     * @param userKey .
     * @return the user created
     * @throws IOException dunno.
     */
    public UserResponse addUser(final String userKey) throws IOException {
        String value = send("/add/" + userKey, "GET");
        ObjectMapper mapper = new ObjectMapper();
        UserResponse readValue = mapper.readValue(value, UserResponse.class);
        return readValue;
    }

    @Override
    final String getControllerRootURL() {
        return "/user";
    }

}
