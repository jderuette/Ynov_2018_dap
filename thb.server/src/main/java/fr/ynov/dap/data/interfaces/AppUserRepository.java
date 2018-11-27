package fr.ynov.dap.data.interfaces;

import org.springframework.data.repository.CrudRepository;

import fr.ynov.dap.data.AppUser;

public interface AppUserRepository extends CrudRepository<AppUser, Integer> {
	AppUser findByUserKey(String userKey);
}
