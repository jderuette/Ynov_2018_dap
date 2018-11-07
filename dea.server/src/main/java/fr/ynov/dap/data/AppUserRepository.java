
package fr.ynov.dap.data;


import org.springframework.data.repository.CrudRepository;


public interface AppUserRepository extends CrudRepository<AppUser, Integer>
{
  public AppUser findByName(String userkey);
}
