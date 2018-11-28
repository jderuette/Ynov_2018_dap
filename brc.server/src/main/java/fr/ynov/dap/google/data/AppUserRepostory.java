package fr.ynov.dap.google.data;

import org.springframework.data.repository.CrudRepository;

/**
 * The Interface AppUserRepostory.
 */
public interface AppUserRepostory extends CrudRepository<AppUser, Integer>{

	/**
	 * Find by userkey.
	 *
	 * @param userKey the user key
	 * @return the app user
	 */
	AppUser findByUserkey(String userKey);
}
