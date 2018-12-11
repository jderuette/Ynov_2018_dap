package fr.ynov.dap.repositories;

import fr.ynov.dap.models.common.User;
import org.springframework.data.repository.CrudRepository;

//TODO grj by Djer |JPA| Si tu n'en à pas besoin, ne cré pas cette interface
public interface GoogleAccountRepository extends CrudRepository<User, Integer> {

}
