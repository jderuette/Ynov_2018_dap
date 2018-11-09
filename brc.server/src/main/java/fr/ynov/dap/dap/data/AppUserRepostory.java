package fr.ynov.dap.dap.data;

import org.springframework.data.repository.CrudRepository;

public interface AppUserRepostory extends CrudRepository<AppUser, Integer>{

	public AppUser findByUserkey(String userKey);
}
