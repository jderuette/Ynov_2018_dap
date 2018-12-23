package fr.ynov.dap.dap.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.api.services.people.v1.PeopleService;

/**
 * @author Florian BRANCHEREAU
 *
 */
@Service
public class PeopleServiceAPI extends GoogleService {

    /**.
     * LOG
     */
    protected static final Logger LOG = LogManager.getLogger();

    /**
     * @throws Exception constructeur
     * @throws IOException constructeur
     */
    public PeopleServiceAPI() throws Exception, IOException {
        super();
    }

    /**
     * @param userKey nom du compte
     * @return BuildPeopleService
     * @throws IOException fonction
     * @throws GeneralSecurityException fonction
     */
    public PeopleService getServicePeople(final String userKey) throws IOException, GeneralSecurityException {
        return buildPeopleService(userKey);
    }

    /**
     * @param userKey Nom du conpte
     * @return peopleservice
     * @throws IOException fonction
     * @throws GeneralSecurityException fonction
     */
    private PeopleService buildPeopleService(final String userKey) throws IOException, GeneralSecurityException {
        PeopleService peopleservice = new PeopleService.Builder(getHttpTransport(), getJsonFactory(),
                getCredentials(userKey))
                        .setApplicationName(getConfiguration().getApplicationName())
                        .build();
      //TODO brf by Djer |Log4J| Contextualise tes messages
        LOG.debug("PeopleServiceAPI : " + peopleservice);
        return peopleservice;
    }

    /**
     * @param userKey Nom du compte
     * @return GetServicePeople
     * @throws IOException fonction
     * @throws GeneralSecurityException fonction
     */
    public int getNbContact(final String userKey) throws IOException, GeneralSecurityException {
        //TODO brf by Djer |API Google| Les "Identifiants" stockés dans le storeCredential pour Google sont des "accountName" PAS des userKey
        return this.getServicePeople(userKey)
                .people()
                .connections()
                .list("people/me")
                .setPersonFields("nicknames")
                .execute()
                .getTotalItems();
    }
}
