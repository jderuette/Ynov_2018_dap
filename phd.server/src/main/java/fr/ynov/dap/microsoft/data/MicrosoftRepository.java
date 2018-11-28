package fr.ynov.dap.microsoft.data;

import org.springframework.data.repository.CrudRepository;

import fr.ynov.dap.data.AppUser;

/**
 *
 * @author Dom .
 *
 */
public interface MicrosoftRepository extends CrudRepository<MicrosoftAccountData, Integer> {
    /**
     * @param accountName .
     * @return .
     */
    AppUser findByAccountName(String accountName);
}
