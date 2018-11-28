package fr.ynov.dap.repository;

import org.springframework.data.repository.CrudRepository;

import fr.ynov.dap.data.*;

public interface AppUserRepository extends CrudRepository<AppUser, Integer> {
	
	AppUser findByUserKey(String userKey);

}
