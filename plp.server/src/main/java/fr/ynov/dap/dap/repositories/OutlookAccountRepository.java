package fr.ynov.dap.dap.repositories;

import fr.ynov.dap.dap.data.microsoft.OutlookAccount;
import org.springframework.data.repository.CrudRepository;

public interface OutlookAccountRepository extends CrudRepository<OutlookAccount, Integer> {
    public OutlookAccount findByName(String accountName);
}
