//TODO thb by Djer |POO| Evite de créer des package "technique" c'est en général une mauvaise idée
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
