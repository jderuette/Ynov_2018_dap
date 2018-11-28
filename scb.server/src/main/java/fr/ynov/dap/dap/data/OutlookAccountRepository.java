package fr.ynov.dap.dap.data;

import org.springframework.data.repository.CrudRepository;

public interface OutlookAccountRepository extends CrudRepository<OutlookAccount, Integer>{
	OutlookAccount findByName(String name);
}
