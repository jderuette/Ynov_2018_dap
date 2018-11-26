package fr.ynov.dap.services;

import fr.ynov.dap.models.User;
import fr.ynov.dap.repositories.UserRepository;
import org.apache.logging.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * UserService
 */
@Service
public class UserService {

    /**
     * Autowired UserRepository
     */
    @Autowired
    private UserRepository userRepository;

    private static final Logger LOGGER = LogManager.getLogger(UserService.class);

    /**
     * Create a new user.
     *
     * @param userName name of the new user-
     * @return A map with to create after the API response.
     */
    public Map<String, String> create(String userName) {

        Map<String, String> response = new HashMap<>();

        boolean isUserAlreadyExists = userRepository.findByName(userName) != null;

        if (!isUserAlreadyExists) {
            try {
                User newUser = new User();
                newUser.setName(userName);
                userRepository.save(newUser);

                response.put("message", "New user " + userName + " is successfully created");
                LOGGER.info("New user created");
            } catch (Exception e) {
                response.put("error", "Error when trying to create new user: " + e.toString());
                LOGGER.error("Error when trying to create new user");
            }
        } else {
            response.put("error", "User already exists");
            LOGGER.info("Failed to create new user, he already exists");
        }

        return response;

    }


}
