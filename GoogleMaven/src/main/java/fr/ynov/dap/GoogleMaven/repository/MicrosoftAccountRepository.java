package fr.ynov.dap.GoogleMaven.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import fr.ynov.dap.GoogleMaven.data.GoogleAccount;
import fr.ynov.dap.GoogleMaven.data.MicrosoftAccount;

import java.util.*;
public interface MicrosoftAccountRepository  extends CrudRepository<MicrosoftAccount, Integer> {

	
	
	@Query("select o from MicrosoftAccount o where o.owner.userKey=:x  ")
	public List<MicrosoftAccount> liMicrosoftAccounts(@Param("x")String userKey);
	
	
}
