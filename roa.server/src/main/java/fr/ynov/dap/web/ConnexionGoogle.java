package fr.ynov.dap.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;

import fr.ynov.dap.Config;
import fr.ynov.dap.services.GoogleCalendar;

/**
 * Classe servant à gérer la connexion à l'API de google surtout concernant
 * la gestion des credentials et des droits d'accès.
 * @author alex
 */
public abstract class ConnexionGoogle {
    /**
     * Creates an authorized Credential object.
     * @param httpTransport The network HTTP Transport.
     * @param user userID
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    public final Credential getCredentials(final NetHttpTransport httpTransport, final String user) throws IOException {
        return this.getFlow(httpTransport).loadCredential(user);
    }
    /**
     * Récupère un objet flow nécessaire pour les credentials.
     * @throws IOException exception
     * @param httpTransport hrrpTransport
     * @return GoogleAuthorizationCodeFlow
     */
    public final GoogleAuthorizationCodeFlow getFlow(final NetHttpTransport httpTransport) throws IOException {
        List<String> scope = new ArrayList<String>();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        //récupération des autorisations
        scope.add(CalendarScopes.CALENDAR_READONLY);
        scope.add(GmailScopes.GMAIL_LABELS);
        //chargement des credentials
        InputStream in = GoogleCalendar.class.getResourceAsStream(Config.getCredentialFilePath());
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(in));
        //interogation de l'API google et récupération des credentials et secrets
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, jsonFactory,
                clientSecrets, scope)
                        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(Config.getClientSecretFile())))
                        .setAccessType("offline").build();
        return flow;
    }
}
