package fr.ynov.dap.repository;

import org.springframework.data.repository.CrudRepository;

import fr.ynov.dap.data.AppUser;

/**
 * @author Mon_PC
 */
public interface AppUserRepository extends CrudRepository<AppUser, Integer> {
    /**
     * @param userName correspondant au nom de l'utilisateur à chercher
     * @return l'utilisateur correspondant à l'userName passé en paaramètre
     */
    AppUser findByName(String userName);
}
