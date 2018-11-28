package fr.ynov.dap.dap.data;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Florian
 */

public interface AppUserRepository extends CrudRepository<AppUser, Integer> {

    /**
     * @param userKey Nom du compte
     * @return .
     */
    AppUser findByName(String userKey);
}
