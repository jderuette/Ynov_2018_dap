package fr.ynov.dap.dap;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;

/**
 * Classe servant à gérer la connexion à l'API de google surtout concernant
 * la gestion des credentials et des droits d'accès.
 * @author alex
 */
@Service
public class ConnexionGoogle {

    /**
     * Récupération de l'objet config par autowire.
     */
    @Autowired
    private Config config;
    /**
     * Getter pour la récupération de l'objet config dans les classes en héritant.
     * @return Config return l'objet config.
     */
    //TODO roa by Djer Un modifier protected serait suffisant.
    // Pas très utile, car chaque service réupère la conf via injection (ce qui est très bien).
    public Config getConfiguration() {
        return this.config;
    }
    /**
     * Creates an authorized Credential object.
     * @param httpTransport The network HTTP Transport.
     * @param user userID
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    public Credential getCredentials(final NetHttpTransport httpTransport, final String user) throws IOException {
        return this.getFlow(httpTransport).loadCredential(user);
    }
    /**
     * Récupère un objet flow nécessaire pour les credentials.
     * @throws IOException exception
     * @param httpTransport hrrpTransport
     * @return GoogleAuthorizationCodeFlow
     */
    public GoogleAuthorizationCodeFlow getFlow(final NetHttpTransport httpTransport) throws IOException {
        List<String> scope = new ArrayList<String>();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        //récupération des autorisations
        scope.add(CalendarScopes.CALENDAR_READONLY);
        scope.add(GmailScopes.GMAIL_LABELS);
        //chargement des credentials
        //TODO roa by Djer tu peux utiliser cette Class là, pour éviter une dépendance peux utile vers GoogleCalendar
        InputStream in = GoogleCalendar.class.getResourceAsStream(config.getCredentialFilePath());
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(in));
        //FIXME roa by Djer Charger une fichier externe au jar ?
        //interogation de l'API google et récupération des credentials et secrets
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, jsonFactory,
                clientSecrets, scope)
                        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(config.getClientSecretFile())))
                        .setAccessType("offline").build();
        return flow;
    }
}
