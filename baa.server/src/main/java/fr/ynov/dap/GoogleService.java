package fr.ynov.dap;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
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

public class GoogleService {

    protected static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static List<String> SCOPES;
    protected NetHttpTransport HTTP_TRANSPORT;
    protected GoogleClientSecrets clientSecrets;
    @Autowired
    protected static Config maConfig;

    /**
     * Constructeur en Singleton de la classe
     * @throws GeneralSecurityException
     * @throws IOException
     */
    //TODO baa by Djer Commentaire Faux, ce n'est pas un Singleton !
    public GoogleService() throws GeneralSecurityException, IOException {
	this.init();
    }
    /**
     * Initialise les paramètres pour les classes filles
     * @throws IOException
     * @throws GeneralSecurityException
     */
    private void init() throws IOException, GeneralSecurityException {
	HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
	SCOPES = new ArrayList<String>();
    }

    /**
     * Creates an authorized Credential object.
     * 
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    protected Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT, String userId) throws IOException {
	//TODO baa by Djer HTTP_TRANSPORT déja présent en attribut de cette classe, pas utile de le passer en paramètre
	SCOPES.add(CalendarScopes.CALENDAR_READONLY);
	SCOPES.add(GmailScopes.GMAIL_READONLY);
	SCOPES.add(PeopleServiceScopes.CONTACTS_READONLY);
	// Load client secrets.

	//TODO baa by Djer utiliser la config ?
	InputStream in = GoogleService.class.getResourceAsStream("/credentials.json");
	clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
	//TODO baa By Djer chargement d'un fichier "externe" au jar ?

	// Build flow and trigger user authorization request.
	GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
		clientSecrets, SCOPES)
		//FIXME baa By Djer utiliser la Config ?
		.setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
		.setAccessType("offline").build();
	return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize(userId);
    }
    /**
     * 
     * @return
     * @throws IOException
     * @throws GeneralSecurityException
     */
    protected GoogleAuthorizationCodeFlow getFlow() throws IOException, GeneralSecurityException {

	// Build flow and trigger user authorization request.
	GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
		clientSecrets, SCOPES)
		.setDataStoreFactory(
			new FileDataStoreFactory(new java.io.File(maConfig.getClientSecretFile())))
		.setAccessType("offline").build();
	return flow;
    }
}
