package fr.ynov.dap.data.google;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.google.common.base.Optional;

import fr.ynov.dap.data.AppUser;

/**
 * @author thibault
 *
 */
public interface GoogleAccountRepository extends CrudRepository<GoogleAccount, Integer> {
    /**
     * Find Google Account by accountNAme.
     * @param accountName accountName of user.
     * @param owner owner of account.
     * @return result of search.
     */
    Optional<GoogleAccount> findByAccountNameAndOwner(String accountName, AppUser owner);

    /**
     * Exist GoogleAccount by accountName.
     * @param accountName account name.
     * @param owner owner of account.
     * @return result of search.
     */
    boolean existsByAccountNameAndOwner(String accountName, AppUser owner);

    /**
     * Delete GoogleAccount by accountName.
     * @param accountName account name.
     * @param owner owner of account.
     */
    void deleteByAccountNameAndOwner(String accountName, AppUser owner);

    /**
     * Delete GoogleAccount by owner.
     * @param owner owner of account.
     */
    void deleteByOwner(AppUser owner);

    /**
     * Delete GoogleAccount by accessToken.
     * @param accessToken accessToken check if exist.
     */
    void deleteByAccessToken(String accessToken);

    /**
     * Exist GoogleAccount by accessToken.
     * @param accessToken accessToken check if exist.
     * @return result of search.
     */
    boolean existsByAccessToken(String accessToken);


    //Optional<GoogleCredential> findByAccessToken(String accessToken);

    /**
     * Find all account name.
     * @param owner owner id of account.
     * @return list of all account names
     */
    @Query("SELECT g.accountName FROM GoogleAccount g WHERE g.owner = ?1")
    Set<String> findAllAccountNamesByOwner(AppUser owner);
}
