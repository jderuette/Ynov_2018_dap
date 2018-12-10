package fr.ynov.dap.dap.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.ynov.dap.dap.data.MicrosoftAccount;;

@Repository
public interface MicrosoftUserRepository extends CrudRepository<MicrosoftAccount, Integer>{

}
