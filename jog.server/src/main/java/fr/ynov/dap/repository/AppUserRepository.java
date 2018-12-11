package fr.ynov.dap.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.ynov.dap.data.AppUser;

@Repository

public interface AppUserRepository extends CrudRepository<AppUser, Integer> {

	// public AppUser findByUserKey(String userId);
	@Query("select o from AppUser o where o.name=:name")
	public AppUser selectByName(@Param("name") String userKey);

}
