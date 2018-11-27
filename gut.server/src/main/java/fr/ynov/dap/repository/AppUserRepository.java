package fr.ynov.dap.repository;

import org.springframework.data.repository.CrudRepository;
import fr.ynov.dap.data.AppUser;

public interface AppUserRepository extends CrudRepository<AppUser, Integer>{
	
	public AppUser findByUserKey(String userKey);
	
	
	
}
