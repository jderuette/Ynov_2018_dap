package fr.ynov.dap.repository;

import org.springframework.data.repository.CrudRepository;

import fr.ynov.dap.data.microsoft.MicrosoftAccount;

public interface MicrosoftAccountRepository extends CrudRepository<MicrosoftAccount, Integer> {

}
