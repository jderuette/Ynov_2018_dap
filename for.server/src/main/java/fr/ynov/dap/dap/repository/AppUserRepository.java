package fr.ynov.dap.dap.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.ynov.dap.dap.data.AppUser;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser, Integer>{

	public AppUser findByUserKey(String userKey);
}
