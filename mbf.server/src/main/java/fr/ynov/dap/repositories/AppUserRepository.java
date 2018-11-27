package fr.ynov.dap.repositories;

import fr.ynov.dap.data.google.AppUser;
import org.springframework.data.repository.CrudRepository;

public interface AppUserRepository extends CrudRepository<AppUser, Integer> {
    public AppUser findByName(String userName);
}
