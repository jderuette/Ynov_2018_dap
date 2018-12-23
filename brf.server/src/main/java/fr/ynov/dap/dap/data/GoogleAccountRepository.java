package fr.ynov.dap.dap.data;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Florian
 */
//TODO brf by Djer |JPA| Si tu n'en as pas besoins, ne créé pas cette interface
public interface GoogleAccountRepository extends CrudRepository<GoogleAccountData, Integer> {

    /**
     * @param accountName Nom du compte
     * @return .
     */
    GoogleAccountData findByAccountName(String accountName);
}
