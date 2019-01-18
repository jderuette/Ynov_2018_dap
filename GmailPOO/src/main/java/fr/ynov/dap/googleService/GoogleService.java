package fr.ynov.dap.googleService;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import fr.ynov.dap.Config;

@Service
public class GoogleService {
    protected final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    @Autowired
    protected Config configuration;
    protected Logger log = LogManager.getLogger();
    protected NetHttpTransport HTTP_TRANSPORT;
    private GoogleClientSecrets clientSecrets;
    //TODO bes by Djer |POO| Cet attribut ne doit pas "persister" entre 2 appells, en effet le "clientSecrets" serait lui persisté, et il est construit à partir de "in" qui du coup devient "temporaire"
    private InputStream in;

    /**
     * @throws IOException
     * @throws GeneralSecurityException
     * @throws UnsupportedOperationException
     * 
     */

    public GoogleService() throws UnsupportedOperationException, GeneralSecurityException, IOException {

        log.info("GoogleService created" + this.toString());

    }

    /**
     * @return the clientSecrets
     */
    public GoogleClientSecrets getClientSecrets() {
        return clientSecrets;
    }

    /**
     * @param clientSecrets the clientSecrets to set
     */
    public void setClientSecrets(final GoogleClientSecrets clientSecrets) {
        this.clientSecrets = clientSecrets;
    }

    /**
     * @return the in
     */
    public InputStream getIn() {
        return in;
    }

    /**
     * @param in the in to set
     */
    public void setIn(InputStream in) {
        this.in = in;
    }

    protected Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT, String user)
            throws IOException, GeneralSecurityException {
        Credential credencial = null;
        if (getIn() != null || configuration.getAllSCOPES() == null) {

            credencial = getFlow().loadCredential(user);

        } else {
            //TODO be by Djer |Log4J| Cette log devrait entreen warn/error.
            //TODO be by Djer |Log4J| MEssage pas très utile, on en sait pas si ce mesage se produit acuase d'un probleme "interne" (in ==null don "init" n'a pas bien fonctionné) ou un probleme de "configuration" (les scopes ne sont pas défini)
            log.info("problem getCredencials user=" + user);

        }
        return credencial;
    }

    public GoogleAuthorizationCodeFlow getFlow() throws IOException, GeneralSecurityException {

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                getClientSecrets(), configuration.getAllSCOPES())
                        .setDataStoreFactory(
                                new FileDataStoreFactory(new java.io.File(configuration.getCredentialFolder())))
                        .setAccessType("offline").build();
        return flow;
    }

    /**
     * @param configuration the configuration to set
     */
    public void setConfiguration(Config configuration) {
        this.configuration = configuration;
    }

    /**
     * @return the configuration
     */
    public Config getConfiguration() {
        return configuration;
    }

    @PostConstruct
    public void init() throws UnsupportedOperationException, GeneralSecurityException, IOException {

        try {

            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            //TODO bes by Djer |POO| Il ya une "dépendance" étrange avec in. Tu le "set", et la ligne suivante tu l'utilise. C'est là qu'o ns'apercoit que "in" à vocation à etre "temporaire" et ne devrait donc pas être un attribut de ta Classe
            setIn(GoogleService.class.getResourceAsStream(configuration.getClientSecretFile()));
            setClientSecrets(GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in)));

        } catch (Exception e) {
            //TODO bes by Djer |Log4J| N'ajoute pas le message de l'exception a ton "propre message". Utilisel e deuxième parametre et laisse Log4J présenter la "cause" de ton message
            log.error("error while intializing Google Service   " + e.getMessage());
        }

    }
}
