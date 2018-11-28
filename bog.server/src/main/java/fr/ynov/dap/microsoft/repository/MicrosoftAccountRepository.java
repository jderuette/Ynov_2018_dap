package fr.ynov.dap.microsoft.repository;

import org.springframework.data.repository.CrudRepository;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.microsoft.data.MicrosoftAccountData;

/**
 * @author Mon_PC
 */
public interface MicrosoftAccountRepository extends CrudRepository<MicrosoftAccountData, Integer> {
    /**
     * @param accountName permettant de retrouver le microsoftAccount associé
     * @return microsoftaccount with his accountname
     */
    AppUser findByAccountName(String accountName);
}
