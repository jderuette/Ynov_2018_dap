package fr.ynov.dap.dap.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface AppUserRepository extends CrudRepository<AppUser, Integer>{
	AppUser findByName(String name);
}
