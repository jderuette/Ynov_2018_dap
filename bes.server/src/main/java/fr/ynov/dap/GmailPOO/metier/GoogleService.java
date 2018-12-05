package fr.ynov.dap.GmailPOO.metier;

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

import fr.ynov.dap.GmailPOO.Config;
@Service
public class GoogleService {
	protected final  JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	@Autowired
	protected  Config configuration;
	//TODO bes by Djer |POO| Si variable en MAJUSCULE alors static final (si tu as vraiment besoin d'un variable de **classe modifiaible** alors écrit la variable en minuscule, mais je ne pense pas que se soit le cas)
	protected  final Logger LOG = LogManager.getLogger();
	/**
	 * @param gMailService
	 * @param calendarService
	 */
	
	

	/**
	 * @throws IOException 
	 * @throws GeneralSecurityException 
	 * @throws UnsupportedOperationException 
	 * 
	 */
    
	public GoogleService() throws UnsupportedOperationException, GeneralSecurityException, IOException {
		
		LOG.info("GoogleService created"+this.toString());
		// TODO Auto-generated constructor stub
	
	}


	//TODO bes by Djer |POO| Positionne tes atteribut en début de classe (Constante, attributs, initialisateur static, constructeur, méthdoe métier, méthode "génériques", getter/setter).
	protected   NetHttpTransport HTTP_TRANSPORT ;
	
	//TODO bes by Djer |POO| Je ne vois pas ou est initialisée cette variable. Elle n'a pas besoin d'être en attribut.
	private  GoogleClientSecrets clientSecrets;
	//TODO bes by Djer |POO| n'est plus utilisé, mais comme "sans modifier" il hérite du modifier "public" de la classe et ton IDE ne peut pas te le signaler.
	private InputStream in;
	
	protected  Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT,String user)
			throws IOException, GeneralSecurityException {
		
		if (in != null || configuration.getAllSCOPES() == null) {
			 
			return getFlow().loadCredential(user);
	       
		} else {
			
			LOG.info("problem getCredencials user="+user);
			
			//TODO bes by Djer |POO| Evite les multiples return dnas une même méthode
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

	public  GoogleAuthorizationCodeFlow getFlow() throws IOException, GeneralSecurityException {
		
// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, configuration.getAllSCOPES())
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
	public  Config getConfiguration() {
		return configuration;
	}
    @PostConstruct
	public  void init() throws UnsupportedOperationException, GeneralSecurityException, IOException {
    	
    	try {
			
		HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		in = GoogleService.class.getResourceAsStream(configuration.getClientSecretFile());
		clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
	
		}
		catch (Exception e) {
			// TODO: handle exception
		    //TODO bes by Djer |Log4J| PAS de sysout sur un server, cela ne saute pas une ligne dans le fichier de logs. Et il ne faut PAS sauter de lignes dans un ficheir de logs (c'est un fichier "technique" qui doit être facilement parssable)
			System.out.println();
			//TODO bes by Djer |Log4J| Met un message claire "error while intializing Google Service". Utilise le deuxième paramètre de la méthode "error" pour mettre la cause ("e") ainsi tu auras le message de l'excetipn PLUS la pile d'éxécution
			LOG.error("Config"+e.getMessage());
			
	}

    }}
