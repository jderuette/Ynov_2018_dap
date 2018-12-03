/**
 * 
 */
package fr.ynov.dap.GmailPOO.dao;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import fr.ynov.dap.GmailPOO.data.AppUser;

/**
 * @author acer
 *
 */
@Repository
public interface AppUserRepository extends CrudRepository<AppUser, Integer>{

  //TODO bes by Djer |Spring| Spring peu te créer ces requetes "simple" en se basant sur le nom de la méthode. Ici le nom de la méthdoe est déja "OK", ton @Query ne fait que "empecher" Spring de faire par defaut pour produire "ta" requete HQL
	@Query("select o from AppUser o where o.userKey=:userKey")
    public  AppUser findByUserkey(@Param("userKey")String userkey);
	
}
  