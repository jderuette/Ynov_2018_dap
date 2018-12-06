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
    //TODO bot by Djer |JPA| A priori tu n'utilises pas cette méthode, ne la cré pas si elle ne te sert pas (PI : toutes cette classe ne devrait plus te servire)
	GoogleAccount findByName(String name);
	
}
