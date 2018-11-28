package fr.ynov.dap.repository;

import org.springframework.data.repository.CrudRepository;

import fr.ynov.dap.data.AppUser;

/**
 * Interface to manage AppUser in the mySQL database.
 * @author Robin DUDEK
 */
public interface AppUserRepository extends CrudRepository<AppUser, Integer> {

    /**
     * Method to find a user by his userKey.
     * @param userKey The name of the user
     * @return User
     */
    AppUser findByUserKey(String userKey);
}
