package fr.ynov.dap.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface qui permet d'intérroger la base et de retourner les infos
 * @author abaracas
 *
 */
//TODO baa by Djer |POO| Pourquoi cette classe n'est pas rangée avec ces copines dans le sous package dao ?
public interface AppUserRepository extends CrudRepository<AppUser, Integer>{
  /**
   * Retourne le user correspondant à la userKey donnée
   * @param userKey l'id de l'utilisateur applicaif
   * @return un AppUser
   */
   @Query ("SELECT o FROM AppUser o WHERE o.name = :name")
   //TODO baa by Djer |JPA| Tu aurais pu renomer ta méthode "findByName", tu n'aurais pas eut à écrire à écrie la Query (Spring l'aurait générée pour toi)
   public AppUser findByUserkey(@Param("name") String userKey);
   
   /**
    * Retourne tous les comptes de l'utilisateur
    * @param userKey l'id de l'utilisateur applicaif
    * @return un AppUser
    */
   //TODO baa by Djer |JPA| Je ne pense pas que cette requete HQL fonctionne; Si tu alias "GoogleAccount" par "o", alors tu va retourner des "GoogleAccount" et pas des AppUser
    @Query ("SELECT o FROM GoogleAccount o WHERE o.owner = :userkey")
    //TODO baa by DJer |POO| Cette méthode n'est jamais appelée, tu peux la supprimer.
    public AppUser accountsByUserkey(@Param("userkey") String userKey);
   
}
