package fr.ynov.dap.repositories;

import fr.ynov.dap.models.common.User;
import org.springframework.data.repository.CrudRepository;

public interface GoogleAccountRepository extends CrudRepository<User, Integer> {

}
