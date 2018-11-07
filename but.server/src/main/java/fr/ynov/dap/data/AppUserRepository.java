package fr.ynov.dap.data;

import org.springframework.data.repository.CrudRepository;

/**
 * @author thibault
 *
 */
public interface AppUserRepository extends CrudRepository<AppUser, Integer> {
    /**
     * Find AppUser by UserKey.
     * @param userKey userKey of user.
     * @return result of search.
     */
    AppUser findByUserKey(String userKey);
}
