package fr.ynov.dap.dap.repositories;

import fr.ynov.dap.dap.data.AppUser;
import org.springframework.data.repository.CrudRepository;

public interface AppUserRepository extends CrudRepository<AppUser, Integer> {
    public AppUser findByName(String userKey);
}
