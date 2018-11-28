package fr.ynov.dap.dap.data;

import org.springframework.data.repository.CrudRepository;

public interface GoogleAccountRepository extends CrudRepository<GoogleAccount, Integer>{
	GoogleAccount findByName(String name);
}
