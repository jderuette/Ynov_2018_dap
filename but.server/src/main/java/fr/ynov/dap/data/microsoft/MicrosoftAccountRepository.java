package fr.ynov.dap.data.microsoft;

import org.springframework.data.repository.CrudRepository;

/**
 * @author thibault
 *
 */
public interface MicrosoftAccountRepository extends CrudRepository<MicrosoftAccount, Integer> {
    /**
     * Exist MicrosoftAccount by accountName.
     * @param accountName account name.
     * @return result of search.
     */
    boolean existsByAccountName(String accountName);
}
