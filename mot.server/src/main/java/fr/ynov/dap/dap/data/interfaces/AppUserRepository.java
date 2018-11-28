package fr.ynov.dap.dap.data.interfaces;

import org.springframework.data.repository.CrudRepository;

import fr.ynov.dap.dap.data.google.AppUser;

/**
 * The Interface AppUserRepository.
 */
public interface AppUserRepository extends CrudRepository<AppUser, Integer> {
	AppUser findByUserKey(String userKey);

}
