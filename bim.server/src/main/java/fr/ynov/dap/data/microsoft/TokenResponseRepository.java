package fr.ynov.dap.data.microsoft;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository.
 * @author MBILLEMAZ
 *
 */
public interface TokenResponseRepository extends CrudRepository<TokenResponse, Integer> {
    /**
     * get token response by microsoft account.
     * @param id account id
     * @return token
     */
    @Query("select token from TokenResponse token join MicrosoftAccount micro on token.id = micro.token where micro.id = ?1")
    TokenResponse findOneByMicrosoftAccount(Integer id);
}
