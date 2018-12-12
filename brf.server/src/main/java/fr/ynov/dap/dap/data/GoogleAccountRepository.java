package fr.ynov.dap.dap.data;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Florian
 */
public interface GoogleAccountRepository extends CrudRepository<GoogleAccountData, Integer> {

    /**
     * @param accountName Nom du compte
     * @return .
     */
    GoogleAccountData findByAccountName(String accountName);
}
