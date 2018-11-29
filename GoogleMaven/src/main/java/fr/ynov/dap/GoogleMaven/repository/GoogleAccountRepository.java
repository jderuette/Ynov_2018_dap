package fr.ynov.dap.GoogleMaven.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import fr.ynov.dap.GoogleMaven.data.GoogleAccount;

import java.util.*;
public interface GoogleAccountRepository  extends CrudRepository<GoogleAccount, Integer> {

	
	//@Query("select o from GoogleAccount o where o.owner.userKey=:x order by o.id desc ")
	//public Page<GoogleAccount> listGoogleAccount(@Param("x")String userKey, Pageable pageable);
	@Query("select o from GoogleAccount o where o.owner.userKey=:x  ")
	public List<GoogleAccount> listGoogleAccount1(@Param("x")String userKey);
	
	
}
