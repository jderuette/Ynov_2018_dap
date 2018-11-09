package fr.ynov.dap.data;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Dom
 *
 */
public interface AppUserRepository extends CrudRepository<AppUser, Integer> {

    /**
     * @param userkey .
     * @return .
     */
    AppUser findByName(String userkey);

}
