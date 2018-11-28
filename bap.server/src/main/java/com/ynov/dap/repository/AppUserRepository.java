package com.ynov.dap.repository;

import org.springframework.data.repository.CrudRepository;

import com.ynov.dap.domain.AppUser;

/**
 * The Interface AppUserRepository.
 */
public interface AppUserRepository extends CrudRepository<AppUser, Integer> {
	
	/**
	 * Find by name.
	 *
	 * @param name the name
	 * @return the app user
	 */
	AppUser findByName(String name);
}
