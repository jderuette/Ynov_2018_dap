package fr.ynov.dap.services.microsoft;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.data.MicrosoftAccount;
import fr.ynov.dap.exceptions.ServiceException;
import fr.ynov.dap.microsoft.auth.AuthHelper;
import fr.ynov.dap.microsoft.auth.TokenResponse;

/**
 * Microsoft Service.
 */
public abstract class MicrosoftService {

    /**
     * Logger used for logs.
     */
    private static Logger log = LogManager.getLogger();

    /**
     * Get logger for child class.
     * @return logger.
     */
    protected static Logger getLog() {
        return log;
    }

    /**
     * Number of max results to display.
     */
    protected static final Integer MAX_RESULTS = 10;

    /**
     * AppUserRepository instantiate thanks to the injection of dependency.
     */
    @Autowired
    private AppUserRepository repository;

    /**
     * Protected repository getter.
     * @return the AppUser repository.
     */
    protected AppUserRepository getRepository() {
        return repository;
    }

    /**
     * userKey of the AppUser. It's used to get credentials data.
     */
    //TODO jaa by Djer |POO| Attention, sera un Singleton, donc partagé entre tous les utilisateurs, tu as le risque pour que (de temsp en temps) une méthode soit appelé avec un userKey d'un autre utilisateur. Ne met jamais de variable d'état dans un service (surtout si c'est un Singleton, mais en général les services sont des Singleton)
    private String userKey;

    /**
     * set the userKey. Used to retrieve data.
     * @param key userKey
     */
    public void setUserKey(final String key) {
        this.userKey = key;
    }

    /**
     * Get the email of the provided Microsoft account.
     * @param account Microsoft account.
     * @return the email.
     * @throws ServiceException exception.
     */
    protected String getEmail(final MicrosoftAccount account) throws ServiceException {
        try {
            return account.getIdToken().getEmail();
        } catch (JsonParseException jpe) {
          //TODO jaa by Djer |log4J| Contextualise tes messages (" for userKey : " + account.getOwner().getgetUserKey() + " and accountName : " + account.getAccountName())
            log.error("Failed to parse json while retrieving the email.", jpe);
            throw new ServiceException("Failed to parse the email", jpe);
        } catch (JsonMappingException jme) {
          //TODO jaa by Djer |log4J| Contextualise tes messages (" for userKey : " + account.getOwner().getgetUserKey() + " and accountName : " + account.getAccountName())
            log.error("Failed to map json while retrieving the email.", jme);
            throw new ServiceException("Fail to map json the email.", jme);
        } catch (IOException ioe) {
          //TODO jaa by Djer |log4J| Contextualise tes messages (" for userKey : " + account.getOwner().getgetUserKey() + " and accountName : " + account.getAccountName())
            log.error("Incorrect stored value while retrieving the email.", ioe);
            throw new ServiceException("Inccorect stored the email", ioe);
        }
    }

    /**
     * Get the tokens of the provided Microsoft account.
     * @param account Microsoft account.
     * @return the tokens.
     * @throws ServiceException exception.
     */
    protected TokenResponse getTokens(final MicrosoftAccount account) throws ServiceException {
        try {
            TokenResponse tokens = account.getTokenResponse();
            if (tokens == null) {
                throw new ServiceException("Failed to retrieve the tokens. Please try to login again.");
            }

            TokenResponse newTokens = AuthHelper.ensureTokens(tokens, getTenantId(account));
            Boolean areTokensDifferent = tokens.getExpirationTime() != newTokens.getExpirationTime();
            if (areTokensDifferent) {
                tokens = newTokens;
                saveNewTokenToRepository(account, tokens);
            }

            return tokens;
        } catch (JsonParseException jpe) {
          //TODO jaa by Djer |log4J| Contextualise tes messages (" for userKey : " + account.getOwner().getgetUserKey() + " and accountName : " + account.getAccountName())
            log.error("Failed to parse json while retrieving the tenantId.", jpe);
            throw new ServiceException("Failed to parse the tenantId", jpe);
        } catch (JsonMappingException jme) {
          //TODO jaa by Djer |log4J| Contextualise tes messages (" for userKey : " + account.getOwner().getgetUserKey() + " and accountName : " + account.getAccountName())
            log.error("Failed to map json while retrieving the tenantId.", jme);
            throw new ServiceException("Fail to map json the tenantId.", jme);
        } catch (IOException ioe) {
          //TODO jaa by Djer |log4J| Contextualise tes messages (" for userKey : " + account.getOwner().getgetUserKey() + " and accountName : " + account.getAccountName())
            log.error("Incorrect stored value while retrieving the tenantId.", ioe);
            throw new ServiceException("Incorrect stored the tenantId", ioe);
        }
    }

    /**
     * Save the token to the repository. Used when the token have been updated.
     * @param account Microsoft account.
     * @param tokens tokens to save.
     * @throws JsonProcessingException exception.
     */
    private void saveNewTokenToRepository(final MicrosoftAccount account, final TokenResponse tokens)
            throws JsonProcessingException {
        //TODO jaa by Djer |POO| N'utilise pas l'attribut "userkey" (tu n'es pas sur qu'il ai été mis à jours), récupère celui de l'acount (account.getOwner().getuserKey())
        AppUser appUser = repository.findByUserKey(userKey);
        appUser.getMicrosoftAccountByName(account.getAccountName()).setTokenResponse(tokens);
        repository.save(appUser);
    }

    /**
     * Get the tenantId of the provided Microsoft account.
     * @param account Microsoft account.
     * @return the tenant Id.
     * @throws ServiceException exception.
     */
    private String getTenantId(final MicrosoftAccount account) throws ServiceException {
        try {
            return account.getIdToken().getTenantId();
        } catch (JsonParseException jpe) {
          //TODO jaa by Djer |log4J| Contextualise tes messages (" for userKey : " + account.getOwner().getgetUserKey() + " and accountName : " + account.getAccountName())
            log.error("Failed to parse json while retrieving the tenantId.", jpe);
            throw new ServiceException("Failed to parse the tenantId", jpe);
        } catch (JsonMappingException jme) {
          //TODO jaa by Djer |log4J| Contextualise tes messages (" for userKey : " + account.getOwner().getgetUserKey() + " and accountName : " + account.getAccountName())
            log.error("Failed to map json while retrieving the tenantId.", jme);
            throw new ServiceException("Fail to map json the tenantId.", jme);
        } catch (IOException ioe) {
          //TODO jaa by Djer |log4J| Contextualise tes messages (" for userKey : " + account.getOwner().getgetUserKey() + " and accountName : " + account.getAccountName())
            log.error("Incorrect stored value while retrieving the tenantId.", ioe);
            throw new ServiceException("Inccorect stored the tenantId", ioe);
        }
    }
}
