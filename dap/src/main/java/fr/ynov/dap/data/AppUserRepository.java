package fr.ynov.dap.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface qui permet d'intérroger la base et de retourner les infos
 * @author abaracas
 *
 */
public interface AppUserRepository extends CrudRepository<AppUser, Integer>{
  /**
   * Retourne le user correspondant à la userKey donnée
   * @param userKey l'id de l'utilisateur applicaif
   * @return un AppUser
   */
   @Query ("SELECT o FROM AppUser o WHERE o.name = :name")
   public AppUser findByUserkey(@Param("name") String userKey);
   
   /**
    * Retourne tous les comptes de l'utilisateur
    * @param userKey l'id de l'utilisateur applicaif
    * @return un AppUser
    */
    @Query ("SELECT o FROM GoogleAccount o WHERE o.owner = :userkey")
    public AppUser accountsByUserkey(@Param("userkey") String userKey);
   
}
