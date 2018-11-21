package fr.ynov.dap.dap.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface AppUserRepository extends CrudRepository<AppUser, Integer> {
	public AppUser findByUserkey(String userkey);
}
