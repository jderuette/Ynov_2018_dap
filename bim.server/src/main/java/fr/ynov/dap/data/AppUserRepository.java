package fr.ynov.dap.data;

import org.springframework.data.repository.CrudRepository;

/**
 * Repository.
 * @author MBILLEMAZ
 *
 */
public interface AppUserRepository extends CrudRepository<AppUser, Integer> {

    /**
     * Return user by name.
     * @param name used to find user
     * @return user
     */
    AppUser findByName(String name);
}
