package fr.ynov.dap.data.dao;

import org.springframework.data.repository.CrudRepository;

import fr.ynov.dap.data.MicrosoftAccount;

/**
 * Interface permettant d'utiliser les fonctions CrudRepository sur du MicrosoftAccount.
 * @author abaracas
 *
 */
public interface MicrosoftAccountRepository extends CrudRepository<MicrosoftAccount, Integer>{

}
