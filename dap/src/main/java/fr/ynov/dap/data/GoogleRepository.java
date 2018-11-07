package fr.ynov.dap.data;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Dom
 *
 */
public interface GoogleRepository extends CrudRepository<GoogleAccountData, Integer> {

    /**
     * @param accountName .
     * @return .
     */
    AppUser findByAccountName(String accountName);

}
