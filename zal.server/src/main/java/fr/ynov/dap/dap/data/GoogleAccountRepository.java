package fr.ynov.dap.dap.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoogleAccountRepository extends CrudRepository<GoogleAccount, Integer> {

}
