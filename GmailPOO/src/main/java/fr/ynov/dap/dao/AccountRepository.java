/**
 * 
 */
package fr.ynov.dap.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.ynov.dap.data.Account;

/**
 * @author acer
 *
 */
@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {

    @Query("select acc from Account acc where acc.owner.userKey=:userKey  ")
    public List<Account> listAccount(@Param("userKey") String userKey);

    @Query("select o from Account o where o.accountName=:x  ")
    public Account findByName(@Param("x") String accountName);

    @Transactional
    @Modifying
    @Query("update Account o set o.tokenType =:tokenType, o.expiresIn =:expiresIn, o.accessToken =:accessToken, o.refreshToken =:refreshToken, o.idToken =:idToken, o.modifierLe =:modifierLe, o.ExpirationTime =:ExpirationTime where o.id =:id")
    public void update(@Param("id") int id, @Param("tokenType") String tokenType, @Param("expiresIn") int expiresIn,
            @Param("accessToken") String accessToken, @Param("refreshToken") String refreshToken,
            @Param("idToken") String idToken, @Param("modifierLe") Date date,
            @Param("ExpirationTime") Date ExpirationTime);

}
