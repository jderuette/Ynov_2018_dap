package fr.ynov.dap.GmailPOO;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
import com.google.api.services.calendar.Calendar.Acl.List;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;

public class GoogleService {
	protected final static JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	
	//TODO bes by Djer Devrait être ijecté
	protected static Config configuration;
	protected final Logger LOG = LogManager.getLogger("");
	
	//TODO bes by Djer Si pas de modifier, c'est celui de la classe qui est utilisé. Ton "initList" est donc public.
	static String initList[] = { GmailScopes.GMAIL_READONLY, CalendarScopes.CALENDAR, };

	static java.util.List list = new ArrayList(Arrays.asList(initList));
	private static java.util.List<String> allSCOPES = list;
	protected static  NetHttpTransport HTTP_TRANSPORT ;
	protected static InputStream in;
	//TOD bes by DJer Jen vois pas ou est initialisé cette variable. Elle n'a pas besoin d'entre en attribut.
	protected static GoogleClientSecrets clientSecrets;

	/*
	 * protected static Credential getCredential(NetHttpTransport UserId) throws
	 * IOException {
	 * 
	 * InputStream in =
	 * GMailService.class.getResourceAsStream(configuration.getClientSecretFile());
	 * if (in != null || allSCOPES==null) { GoogleClientSecrets clientSecrets =
	 * GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
	 * 
	 * return new AuthorizationCodeInstalledApp( new
	 * GoogleAuthorizationCodeFlow.Builder(UserId, JSON_FACTORY, clientSecrets,
	 * allSCOPES) .setDataStoreFactory(new FileDataStoreFactory(new
	 * java.io.File("tokens"))) .setAccessType("offline").build(), new
	 * LocalServerReceiver()).authorize("user"); } else {
	 * System.out.println("null"); return null;
	 * 
	 * } }
	 */
	protected static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
			throws IOException, GeneralSecurityException {

		
		if (in != null || allSCOPES == null) {
			
		    //TODO bes by Djer "AuthorizationCodeInstalledApp" n'est pas adapté en mode "web". Utilie getFLow.loadCredential(userId)
		    //TODO bes By Djer Attention pour le "multi-compte", il faut un userId (ou userKey) à la palce de "user"
			return new AuthorizationCodeInstalledApp(getFlow(), new LocalServerReceiver()).authorize("user");
		} else {
		    //TODO bes by DJer Pas de sysout sur un server !
			System.out.println("null");
			//TODO bes by Djer Evite les multiples return dnas une même méthode
			return null;

		}
// Load client secrets.
		/*
		 * InputStream in =
		 * GoogleService.class.getResourceAsStream(configuration.getCredentialFolder());
		 * GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
		 * new InputStreamReader(in));
		 * 
		 * // Build flow and trigger user authorization request.
		 * GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
		 * HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, allSCOPES)
		 * .setDataStoreFactory(new FileDataStoreFactory(new
		 * java.io.File(configuration.getClientSecretFile()))) .setAccessType("offline")
		 * .build(); return new AuthorizationCodeInstalledApp(flow, new
		 * LocalServerReceiver()).authorize("user");
		 */
	}

	protected static GoogleAuthorizationCodeFlow getFlow() throws IOException, GeneralSecurityException {
		
// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, allSCOPES)
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
	protected static Config getConfiguration() {
		return configuration;
	}

	static void init() throws UnsupportedOperationException, GeneralSecurityException, IOException {
		try {
		HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		in = GoogleService.class.getResourceAsStream(configuration.getCredentialFolder());
		clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}
}
