package com.ynov.dap.repository;

import org.springframework.data.repository.CrudRepository;

import com.ynov.dap.domain.AppUser;

public interface AppUserRepository extends CrudRepository<AppUser, Integer> {
	AppUser findByName(String name);
}
