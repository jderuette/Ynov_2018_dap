package fr.ynov.dap.google.data;

import org.springframework.data.repository.CrudRepository;

public interface AppUserRepostory extends CrudRepository<AppUser, Integer>{

	AppUser findByUserkey(String userKey);
}
