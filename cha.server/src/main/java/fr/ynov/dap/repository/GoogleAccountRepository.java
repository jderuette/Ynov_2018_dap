package fr.ynov.dap.repository;

import org.springframework.data.repository.CrudRepository;

import fr.ynov.dap.data.GoogleAccount;

/**
 * The Interface GoogleAccountRepository.
 */
//TODO cha by Djer |JPA| Deviendra inutile lorsque tu sauvegarderas en utilisant le AppUser
public interface GoogleAccountRepository extends CrudRepository<GoogleAccount, Integer> {

}
