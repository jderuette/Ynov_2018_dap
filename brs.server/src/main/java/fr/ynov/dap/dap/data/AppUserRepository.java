package fr.ynov.dap.dap.data;

import org.springframework.data.repository.CrudRepository;



public interface AppUserRepository extends CrudRepository<AppUser, Integer> {

	
	
	AppUser findByUserkey(String userkey);

	
	
}
