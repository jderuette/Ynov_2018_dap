package fr.ynov.dap.contract;

import org.springframework.data.repository.CrudRepository;

import fr.ynov.dap.model.AppUser;

/**
 * Interface to manage AppUser from and to database.
 * @author Kévin Sibué
 *
 */
public interface AppUserRepository extends CrudRepository<AppUser, Integer> {

    /**
     * Method to find a user by his userKey.
     * @param userkey Current user id
     * @return User
     */
    AppUser findByUserKey(String userkey);

}
