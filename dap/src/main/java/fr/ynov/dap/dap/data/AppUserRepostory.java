package fr.ynov.dap.dap.data;

import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author David_tepoche
 *
 */
public interface AppUserRepostory extends CrudRepository<AppUser, Integer> {

    /**
     * get the appUser by the userKey.
     *
     * @param userKey name of the account
     * @return appuser
     */
    AppUser findByUserKey(String userKey);

}
