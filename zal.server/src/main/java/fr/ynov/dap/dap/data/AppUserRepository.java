package fr.ynov.dap.dap.data;

import org.springframework.data.repository.CrudRepository;

/**
 * The Interface AppUserRepository.
 */
public interface AppUserRepository extends CrudRepository<AppUser, Integer> {

	/**
	 * Find by userkey.
	 *
	 * @param userkey
	 *            the userkey
	 * @return the app user
	 */
	AppUser findByUserkey(String userkey);
}
