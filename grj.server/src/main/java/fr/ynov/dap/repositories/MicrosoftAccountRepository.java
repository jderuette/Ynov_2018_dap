package fr.ynov.dap.repositories;

import fr.ynov.dap.models.User;
import org.springframework.data.repository.CrudRepository;

public interface MicrosoftAccountRepository extends CrudRepository<User, Integer> {
}
