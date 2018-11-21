package fr.ynov.dap.repository;

import fr.ynov.dap.data.AppUser;
import org.springframework.data.repository.CrudRepository;

public interface AppUserRepository extends CrudRepository<AppUser, Integer> {
  public AppUser findByUserKey(String userKey);
}
