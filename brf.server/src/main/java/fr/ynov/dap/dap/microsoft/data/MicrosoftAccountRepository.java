package fr.ynov.dap.dap.microsoft.data;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Florian
 */
public interface MicrosoftAccountRepository extends CrudRepository<MicrosoftAccountData, Integer> {

    /**
     * @param accountName Nom du compte
     * @return .
     */
    MicrosoftAccountData findByAccountName(String accountName);
}
