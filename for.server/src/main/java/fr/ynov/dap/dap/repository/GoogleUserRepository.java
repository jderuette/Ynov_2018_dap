package fr.ynov.dap.dap.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.GoogleAccount;;

@Repository
public interface GoogleUserRepository extends CrudRepository<GoogleAccount, Integer>{

}
