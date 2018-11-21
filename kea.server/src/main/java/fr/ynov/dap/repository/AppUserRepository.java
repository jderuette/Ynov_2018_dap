package fr.ynov.dap.repository;

import fr.ynov.dap.data.AppUser;
import org.springframework.data.repository.CrudRepository;

/**
 * interface implemented by Hibernate.
 * @author Antoine
 *
 */
public interface AppUserRepository extends CrudRepository<AppUser, Integer> {
  /**
   * Method declared in CrudRepository that finds a user with the generated id.
   * @param userId generated in AppUser
   * @return the user with the id(userKey)
   */
  public AppUser findByUserKey(String userId);
}
