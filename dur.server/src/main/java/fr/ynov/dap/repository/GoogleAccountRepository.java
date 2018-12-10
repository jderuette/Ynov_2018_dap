package fr.ynov.dap.repository;

import org.springframework.data.repository.CrudRepository;

import fr.ynov.dap.data.GoogleAccount;

//TODO dur by Djer |JPA| Si tu n'en a pas besoin, ne créé pas cette interface
public interface GoogleAccountRepository extends CrudRepository<GoogleAccount, Integer> {

}
