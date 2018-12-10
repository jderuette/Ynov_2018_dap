package fr.ynov.dap.GoogleMaven.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import fr.ynov.dap.GoogleMaven.data.AppUser;
import fr.ynov.dap.GoogleMaven.data.GoogleAccount;

public interface AppUserRepostory extends CrudRepository<AppUser, Integer> {

	
	//TODO elj by Djer |JPA| Il n'est pas utile de préciser ta Query, c'est excatement cette requete que Spring va générer pour toi
	@Query("select o from AppUser o where o.userKey=:x  ")
	public AppUser findByUserKey(@Param("x") String userKey);
}
