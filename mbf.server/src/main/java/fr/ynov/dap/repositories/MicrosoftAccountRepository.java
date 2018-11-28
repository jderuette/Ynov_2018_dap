package fr.ynov.dap.repositories;

import fr.ynov.dap.data.microsoft.MicrosoftAccount;
import org.springframework.data.repository.CrudRepository;

public interface MicrosoftAccountRepository extends CrudRepository<MicrosoftAccount, Integer> {
    MicrosoftAccount findByAccountName(String accountName);
}
