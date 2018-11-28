package fr.ynov.dap.repository;

import org.springframework.data.repository.CrudRepository;

import fr.ynov.dap.data.microsoft.MicrosoftAccount;

public interface MicrosoftUserRepository extends CrudRepository<MicrosoftAccount, Integer> {

}
