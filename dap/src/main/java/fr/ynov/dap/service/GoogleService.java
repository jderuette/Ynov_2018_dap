package fr.ynov.dap.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.people.v1.PeopleServiceScopes;

import fr.ynov.dap.Config;
/**
 * Gère les config de l'api google et la création des flows.
 * @author abaracas
 *
 */
public class GoogleService {

    protected static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();   
    //TODO baa by Djer |POO| Le nom en majusucle indique que cette attribut devrait etre static FINAL
    private static List<String> SCOPES;
  //TODO baa by Djer |POO| Le nom en majusucle indique que cette attribut devrait etre static FINAL
    protected NetHttpTransport HTTP_TRANSPORT;
    protected GoogleClientSecrets clientSecrets;
    @Autowired
    protected Config maConfig;
  //TODO baa by Djer |Log4J| Devrait être final (la (pseudo) référence ne sera pas modifiée)
    private static Logger LOG = LogManager.getLogger();

    /**
     * Constructeur en Singleton de la classe
     * @throws GeneralSecurityException exception
     * @throws IOException exception
     */
    public GoogleService() throws GeneralSecurityException, IOException {
	this.init();
    }
    /**
     * Initialise les paramètres pour les classes filles
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */
    private void init() throws IOException, GeneralSecurityException {
	HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
	SCOPES = new ArrayList<String>();
	SCOPES.add(CalendarScopes.CALENDAR_READONLY);
	LOG.info("Ajout du SCOPE calendar");
	SCOPES.add(GmailScopes.GMAIL_READONLY);
	LOG.info("Ajout du SCOPE GMAIL_READONLY");
	SCOPES.add(PeopleServiceScopes.CONTACTS_READONLY);
	LOG.info("Ajout du SCOPE CONTACTS_READONLY");
    }

    /**
     * Creates an authorized Credential object.
     * 
     * @param  userKey id de l'utilisateur
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     * @throws GeneralSecurityException  exception
     */
    public Credential getCredentials(String userKey) throws IOException, GeneralSecurityException {
	try {
	    //TODO baa by Djer |Log4J| Cette log est ptentiellement fasse. A cette ligen de code, tu ne sait pas si cela à fonctionné. Si une exception devait être levée ca sera sur la ligne suivante.
	    LOG.info("Load Credential pour l'utilisateur " + userKey + "a reussi.");
	    //TODO baa by Djer |POO| Evite les multiples return dans une même méthode
	    return getFlow().loadCredential(userKey);
	}
	catch (Exception e) {
	  //TODO baa by Djer |Log4J| N'ajoute pas l'exception dans ton message de cette façon, elle sera au mieux "mal affichée". Utilise le deuxième paramère ("cause") et laisse Log4J présenter correctement cette excetpion dans les logs
	    LOG.error("Load Credential pour l'utilisateur " + userKey + " a échoué. Erreur : " + e);
	    return null;
	}
    }
    /**
     * S'occupe de récupére l'autorisation de l'utilisateur.
     * @return credential le credential
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */
    protected GoogleAuthorizationCodeFlow getFlow() throws IOException, GeneralSecurityException {
	
	//TODO baa by Djer |Design Patern| Chargempent d'un fichier externe au jar ? (utilise un InputStream)
	InputStream in = GoogleService.class.getResourceAsStream(maConfig.getCredentialFolder());
	setClientSecrets(GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in)));
	// Build flow and trigger user authorization request.
	GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
		getClientSecrets(), SCOPES)
		.setDataStoreFactory(
			new FileDataStoreFactory(new java.io.File(maConfig.getClientSecretFile().toString())))
		.setAccessType("offline").build();
	return flow;
    }
    
    /**
     * Récupère le credential google.
     * @return le credential.
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */
    public  DataStore<StoredCredential> getCredentialDatastore() throws IOException, GeneralSecurityException {
	
	return getFlow().getCredentialDataStore();
    }
    /**
     * @return the sCOPES
     */
    //TODO baa by Djer |POO| Personne n'en a besoin en dehors des classes filles (qui peuvent acceder directement à l'attribut car il est protected). Supprime tous ces getter/setter inutiles
    public static List<String> getSCOPES() {
        return SCOPES;
    }
    /**
     * @param sCOPES the sCOPES to set
     */
    public static void setSCOPES(List<String> sCOPES) {
        SCOPES = sCOPES;
    }
    /**
     * @return the hTTP_TRANSPORT
     */
    public NetHttpTransport getHTTP_TRANSPORT() {
        return HTTP_TRANSPORT;
    }
    /**
     * @param hTTP_TRANSPORT the hTTP_TRANSPORT to set
     */
    public void setHTTP_TRANSPORT(NetHttpTransport hTTP_TRANSPORT) {
        HTTP_TRANSPORT = hTTP_TRANSPORT;
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
    public void setClientSecrets(GoogleClientSecrets clientSecrets) {
        this.clientSecrets = clientSecrets;
    }
    /**
     * @return the maConfig
     */
    public Config getMaConfig() {
        return maConfig;
    }
    /**
     * @param maConfig the maConfig to set
     */
    public void setMaConfig(Config maConfig) {
        this.maConfig = maConfig;
    }
    /**
     * @return the jsonFactory
     */
    public static JsonFactory getJsonFactory() {
        return JSON_FACTORY;
    }
    
    
}
