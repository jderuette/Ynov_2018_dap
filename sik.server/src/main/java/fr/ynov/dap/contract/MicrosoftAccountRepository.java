package fr.ynov.dap.contract;

import org.springframework.data.repository.CrudRepository;

import fr.ynov.dap.model.microsoft.MicrosoftAccount;

/**
 * Interface to manage MicrosoftAccount from and to database.
 * @author Kévin Sibué
 *
 */
public interface MicrosoftAccountRepository extends CrudRepository<MicrosoftAccount, Integer> {

}
