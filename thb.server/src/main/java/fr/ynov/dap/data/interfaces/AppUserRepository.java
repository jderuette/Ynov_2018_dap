package fr.ynov.dap.data.interfaces;

import org.springframework.data.repository.CrudRepository;

import fr.ynov.dap.data.AppUser;

/**
 * The Interface AppUserRepository.
 */
public interface AppUserRepository extends CrudRepository<AppUser, Integer> {

	/**
	 * Find by user key.
	 *
	 * @param userKey the user key
	 * @return the app user
	 */
	AppUser findByUserKey(String userKey);
}
