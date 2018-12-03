/**
 * 
 */
package fr.ynov.dap.GmailPOO.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.ynov.dap.GmailPOO.data.Account;


/**
 * @author acer
 *
 */
@Repository
public interface AccountRepository extends CrudRepository<Account, Integer>{
    //TODO bes by Djer |Spring| Spring peu te créer ces requetes "simple" en se basant sur le nom de la méthode. Cf Doc Spring : https://docs.spring.io/spring-data/jpa/docs/2.0.5.RELEASE/reference/html/#repositories.query-methods.query-lookup-strategies. Ici un listAccountByUserKeyOrderByIdDesc(String userKey) aurait fonctionné.
    //TODO bes by Djer |POO| Si tu utilise des âramètre donne leur un nom claire (x est userKey ici), et "0" est "account" (ou "acc" si tu veux éviter une ambiguité avec le nom de l'entité)
	@Query("select o from Account o where o.owner.userKey=:x order by o.id desc ")
	public  Page<Account> listAccount(@Param("x")String userKey, Pageable pageable) ;
	
	//TODO bes by Djer |Spring| Utiliser la technique des "query by method name" de Spring te permetrais d'avoir un nom de méthode plus claire...
	@Query("select o from Account o where o.owner.userKey=:x  ")
	public List<Account> listAccount1(@Param("x")String userKey);
	@Query("select o from Account o where o.accountName=:x  ")
	public Account findByName(@Param("x")String accountName);

	@Transactional
	@Modifying
	@Query("update Account o set o.tokenType =:tokenType, o.expiresIn =:expiresIn, o.accessToken =:accessToken, o.refreshToken =:refreshToken, o.idToken =:idToken, o.modifierLe =:modifierLe, o.ExpirationTime =:ExpirationTime where o.id =:id")
	public void update(@Param("id")int id,@Param("tokenType")String tokenType ,@Param("expiresIn")int expiresIn,
			@Param("accessToken")String accessToken ,@Param("refreshToken")String refreshToken,@Param("idToken")String idToken, @Param("modifierLe") Date date,@Param("ExpirationTime") Date ExpirationTime);

	
}
