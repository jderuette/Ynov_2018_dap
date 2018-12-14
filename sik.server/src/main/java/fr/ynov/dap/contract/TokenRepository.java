package fr.ynov.dap.contract;

import org.springframework.data.repository.CrudRepository;

import fr.ynov.dap.microsoft.model.TokenResponse;

/**
 * Interface to manage TokenResponse from and to database.
 * @author Kévin Sibué
 *
 */
//TODO sik by Djer |JPA| Tu n'utilises pas cette Interface, ne la créé pas.
public interface TokenRepository extends CrudRepository<TokenResponse, Integer> {

}
