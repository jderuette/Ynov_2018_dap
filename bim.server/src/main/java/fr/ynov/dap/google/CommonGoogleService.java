package fr.ynov.dap.google;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.HashSet;

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
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.people.v1.PeopleServiceScopes;

import fr.ynov.dap.Config;

/**
 * Abstract class for services classes.
 * @author MBILLEMAZ
 *
 */

@Service
public abstract class CommonGoogleService {

    /**
     * json factory.
     */
    protected static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * rights asked to API.
     */
    protected static final HashSet<String> SCOPES = new HashSet<String>();

    /**
     * http transport.
     */
    //TODO bim by Djer |POO| Pourquoi static ? CommonGoogleService est un singleton donc si tu le laisses en variable d'instance tu n'en auras qu'un seul
    private static NetHttpTransport httpTransport = null;

    /**
     * config file.
     */
    @Autowired
    private Config config;

    /**
     * Generate http transport attribute.
     */
    public CommonGoogleService() {
        try {
            CommonGoogleService.httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            CommonGoogleService.SCOPES.add(CalendarScopes.CALENDAR_READONLY);
            CommonGoogleService.SCOPES.add(GmailScopes.GMAIL_LABELS);
            CommonGoogleService.SCOPES.add(PeopleServiceScopes.CONTACTS_READONLY);
        } catch (GeneralSecurityException e) {
          //TODO bim by Djer |Log4J| Traite ce TO-DO !
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
          //TODO bim by Djer |Log4J| Traite ce TO-DO !
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * Returns credentials.
     * @param userKey Applicative user
     * @return credential file
     * @throws Exception if user not found
     */
    protected final Credential getCredentials(final String userKey) throws Exception {
        Logger logger = LogManager.getLogger();
        //TODO bim by Djer |Log4J| Contextualise tes messages de log ("for userKey : " + userKey)
        logger.info("Récupération des droits des API google...");
        GoogleAuthorizationCodeFlow flow = getFlow();
        Credential cred = flow.loadCredential(userKey);
        if (cred == null) {
            throw new Exception("Utilisateur inexistant");
        }
        return cred;
    }

    /**
     * @return the httpTransport
     */
    //TODO bim by Djer |POO| Evite de laisser trainer un "getter" au milieu des méthdoes métiers (Ordre : Constantes, variables de classe, variables d'instance, initialisateur static, constructeurs, méthodes métiers, méthode génériques (toString, compareTo,...), getter/setter)
    public static NetHttpTransport getHttpTransport() {
        return httpTransport;
    }

    /**
     * Get google flow.
     * @return google glow
     */
    public final GoogleAuthorizationCodeFlow getFlow() {

        try {
            File file = new File(this.config.getCredentialsFolder() + "credentials.json");
          //TODO bim by Djer |Log4J| PAS de SysOut sur un Serveur ! Utilise une Log
            System.out.println(file.getPath());
            InputStreamReader in = new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8"));
            GoogleClientSecrets clientSecrets;
            clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, in);
            //TODO bim by Djer |POO| Evite les multiples return dans une même méthode.
            return new GoogleAuthorizationCodeFlow.Builder(CommonGoogleService.httpTransport, JSON_FACTORY,
                    clientSecrets, SCOPES)
                            .setDataStoreFactory(new FileDataStoreFactory(new File(this.config.getClientSecretDir())))
                            .setAccessType("offline").build();
        } catch (FileNotFoundException e) {
            //TODO bim by Djer |IDE| Supprime les TO-DO qui ne sont plus valides !
            // TODO Auto-generated catch block*
            //TODO bim by Djer |Log4J| Evite de créer un Logger "à la volé" c'est couteux. Cré le en static final dans la classe, ca évitera que tu soit tenté d'en créer pluieurs pour la même catégorie
            Logger logger = LogManager.getLogger();
            logger.error("impossible de récupérer le fichier credentials.json dans le dossier "
                    + this.config.getCredentialsFolder());
            //TODO bim by Djer |Log4J| "e.printStackTrace()" affiche dans la console de façon bien déguelasse ! Utilise le deuxième parametre d'un log.xxx() (la cause) pour afficher le message PLUS la pile d'éxécution
            e.printStackTrace();
        } catch (IOException e) {
            //TODO bim by Djer |Log4J| Traite ce TO-DO !
            // TODO Auto-generated catch block
          //TODO bim by Djer |Log4J| "e.printStackTrace()" affiche dans la console
            e.printStackTrace();
        }
        return null;

    }

    /**
     * @return the config
     */
    //TOD bim by Djer |POO| Pourquoi en "public" protected serait suffisant. Moins tu exposes ton code mieux c'est ! 
    public final Config getConfig() {
        return config;
    }

}
