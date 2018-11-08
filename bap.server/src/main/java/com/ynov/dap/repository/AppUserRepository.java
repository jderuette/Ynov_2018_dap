package com.ynov.dap.repository;

import org.springframework.data.repository.CrudRepository;
import com.ynov.dap.data.AppUser;

public interface AppUserRepository extends CrudRepository<AppUser, Integer> {
	public AppUser findByName(String name);
}