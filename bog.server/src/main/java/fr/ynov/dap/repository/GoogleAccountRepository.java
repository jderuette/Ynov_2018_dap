package fr.ynov.dap.repository;

import org.springframework.data.repository.CrudRepository;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.GoogleAccountData;

/**
 * @author Mon_PC
 */
public interface GoogleAccountRepository extends CrudRepository<GoogleAccountData, Integer> {
    /**
     * @param accountName permettant de retrouver le googleAccount associé
     * @return googleaccount with his accountname
     */
    AppUser findByAccountName(String accountName);
}
