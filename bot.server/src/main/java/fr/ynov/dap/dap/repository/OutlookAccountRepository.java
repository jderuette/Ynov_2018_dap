package fr.ynov.dap.dap.repository;

import org.springframework.data.repository.CrudRepository;

import fr.ynov.dap.dap.data.OutlookAccount;

/**
 * The Interface OutlookAccountRepository.
 */
public interface OutlookAccountRepository extends CrudRepository<OutlookAccount, Integer> {
	
	/**
	 * Find by name.
	 *
	 * @param name the name
	 * @return the outlook account
	 */
	OutlookAccount findByName(String name);
}
