package fr.ynov.dap.data;

import org.springframework.data.repository.CrudRepository;

/**
 * @author alex
 *
 */
public interface AppUserRepository extends CrudRepository<AppUser, Integer> {
  /**
   * @param userKey userkey
   * @return user
   */
  AppUser findByUserKey(String userKey);
}
