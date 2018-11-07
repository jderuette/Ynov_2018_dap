package fr.ynov.dap.data;

import org.springframework.data.repository.CrudRepository;

/**
 * @author adrij
 *
 */
public interface AppUserRepository extends CrudRepository<AppUser, Integer> {
    /**
     * find AppUser by userKey.
     * @param userkey userkey.
     * @return AppUser
     */
    AppUser findByUserKey(String userkey);
}
