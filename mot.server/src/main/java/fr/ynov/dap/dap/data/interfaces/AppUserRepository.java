//TODO mot by Djer |POO| Ce n'est aps top d'utiliser "interface" dans un nom de package, les packages ont des noms "fonctionnelles". Ici il s'agit de "service d'accès aux données" ("interne" pour la BDD, "externe" pour les API Microsoft). Globalement ces classe seraient mieux dans un package "service". On utilise aussi souvent DAO (Data Access Object)
package fr.ynov.dap.dap.data.interfaces;

import org.springframework.data.repository.CrudRepository;

import fr.ynov.dap.dap.data.google.AppUser;

/**
 * The Interface AppUserRepository.
 */
public interface AppUserRepository extends CrudRepository<AppUser, Integer> {
	AppUser findByUserKey(String userKey);

}
