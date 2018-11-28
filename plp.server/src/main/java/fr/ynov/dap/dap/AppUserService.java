package fr.ynov.dap.dap;


import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.Map;

@Service
public class AppUserService {
    /**
     * instanciate a AppUserRepository
     */
    private final AppUserRepository repository;

    @Autowired
    public AppUserService(AppUserRepository repository) {
        this.repository = repository;
    }

    /**
     *
     * @param userKey : userKey of user
     * @return Map : return a map with id and username
     */
    public Map<String, Object> addUser(@PathVariable final String userKey) {
        Map<String, Object> response = new HashMap<>();
        if (repository.findByName(userKey) == null) {
            AppUser newUser = new AppUser();
            newUser.setName(userKey);
            repository.save(newUser);
            response.put("id", newUser.getId());
            response.put("username", newUser.getName());
        } else {
            AppUser user = repository.findByName(userKey);
            response.put("id", user.getId());
            response.put("username", user.getName());
        }

        return response;
    }
}
