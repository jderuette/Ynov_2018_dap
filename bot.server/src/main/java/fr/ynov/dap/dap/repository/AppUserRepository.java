package fr.ynov.dap.dap.repository;

import org.springframework.data.repository.CrudRepository;

import fr.ynov.dap.dap.data.AppUser;

/**
 * The Interface AppUserRepository.
 */
public interface AppUserRepository extends CrudRepository<AppUser, Integer>{
	
	/**
	 * Find by name.
	 *
	 * @param name the name
	 * @return the app user
	 */
	AppUser findByName(String name);
}
