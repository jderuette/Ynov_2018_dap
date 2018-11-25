package com.ynov.dap.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ynov.dap.data.AppUser;

public interface AppUserRepository extends CrudRepository<AppUser, Integer> {
	AppUser findByName(String name);
}
