package fr.ynov.dap.dap.microsoft.data;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Florian
 */
//TODO brf by Djer |JPA| Si tu n'en a pas besoin ne créé pas cette interface
public interface MicrosoftAccountRepository extends CrudRepository<MicrosoftAccountData, Integer> {

    /**
     * @param accountName Nom du compte
     * @return .
     */
    MicrosoftAccountData findByAccountName(String accountName);
}
