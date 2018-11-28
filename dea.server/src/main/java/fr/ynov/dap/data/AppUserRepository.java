
package fr.ynov.dap.data;


import org.springframework.data.repository.CrudRepository;

/**
 * Interface AppUserRepository
 * @author antod
 *
 */
public interface AppUserRepository extends CrudRepository<AppUser, Integer>
{
  /**
   * Cherche un appUser selon son nom
   * @param userkey
   * @return
   */
  public AppUser findByName(String userKey);
}
