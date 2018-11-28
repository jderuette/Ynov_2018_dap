package fr.ynov.dap.dap.repository;

import org.springframework.data.repository.CrudRepository;

import fr.ynov.dap.dap.data.GoogleAccount;

/**
 * The Interface GoogleAccountRepository.
 */
public interface GoogleAccountRepository extends CrudRepository<GoogleAccount, Integer> {

	/**
	 * Find by name.
	 *
	 * @param name the name
	 * @return the google account
	 */
	GoogleAccount findByName(String name);
	
}
