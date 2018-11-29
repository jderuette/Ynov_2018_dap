package fr.ynov.dap.data.dao;

import org.springframework.data.repository.CrudRepository;

import fr.ynov.dap.data.GoogleAccount;

/**
 * Interface permettant d'utiliser les fonctions CrudRepository sur du GoogleAccount.
 * @author abaracas
 *
 */
public interface GoogleAccountRepository extends CrudRepository<GoogleAccount, Integer>{

}
