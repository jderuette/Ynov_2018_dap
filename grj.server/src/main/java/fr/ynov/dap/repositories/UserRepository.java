package fr.ynov.dap.repositories;

import fr.ynov.dap.models.common.User;
import org.springframework.data.repository.CrudRepository;

/**
 * UserRepository
 */
public interface UserRepository extends CrudRepository<User, Integer> {

    /**
     * Find a user by is Name.
     *
     * @param name user's name that we want to found.
     * @return if user found return User else null.
     */
    User findByName(String name);

}
