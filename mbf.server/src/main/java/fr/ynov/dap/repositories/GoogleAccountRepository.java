package fr.ynov.dap.repositories;

import fr.ynov.dap.data.google.GoogleAccount;
import org.springframework.data.repository.CrudRepository;

public interface GoogleAccountRepository extends CrudRepository<GoogleAccount, Integer> {
    GoogleAccount findByAccountName(String accountName);
}
