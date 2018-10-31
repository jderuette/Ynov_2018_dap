package fr.ynov.dap.dap.service;

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

import fr.ynov.dap.dap.config.Config;

/**
 * 
 * @author Florian BRANCHEREAU
 *
 */
public abstract class GoogleService {
	private static NetHttpTransport HTTP_TRANSPORT;
    private static JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static List<String> SCOPES = new ArrayList<String>();
    protected final static Logger LOG = LogManager.getLogger();
    @Autowired
    protected Config configuration;
    
    /**
     * Constructor class MainService
     * @throws Exception
     * @throws IOException
     */
    public GoogleService() throws Exception, IOException
    {
    	HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    	Init();
    }
    
    /**
     * 
     * @return HTTP_TRANSPORT
     */
    public NetHttpTransport GetHttpTransport()
    {
    	return HTTP_TRANSPORT;
    }
    
    /**
     * 
     * @param configuration
     */
    public void setConfig(Config config)
    {
    	configuration = config;
    }
    

    /**
     * 
     * @return SCOPES
     */
    protected List<String> GetScopes()
    {
    	return SCOPES;
    }

    /**
     * 
     * @return JSON_FACTORY
     */
    protected  JsonFactory GetJsonFactory()
    {
    	return JSON_FACTORY;
    }
    /**
     * 
     * @param jsonFactory
     */
    protected void SetJsonFactory(JsonFactory jsonFactory)
    {
    	JSON_FACTORY = jsonFactory;
    }
    /**
     * Initialisation de SCOPES
     * Appel le constructeur et la classe
     */
    private void Init()
    {
    	SCOPES.add(GmailScopes.GMAIL_LABELS);
    	SCOPES.add(CalendarScopes.CALENDAR_READONLY);
    	SCOPES.add(PeopleServiceScopes.CONTACTS_READONLY);
    }
    /**
     * 
     * @param HTTP_TRANSPORT
     * @return AuthorizationCodeInstalledApp
     * @throws IOException
     * @throws GeneralSecurityException 
     */
    protected Credential getCredentials(String userKey) throws IOException, GeneralSecurityException
    {
        GoogleAuthorizationCodeFlow flow = getFlow();
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize(userKey);
    }
    
    /**
     * 
     * @return flow
     * @throws IOException
     * @throws GeneralSecurityException
     */
	protected GoogleAuthorizationCodeFlow getFlow() throws IOException, GeneralSecurityException
	{
        InputStream in = GoogleService.class.getResourceAsStream(configuration.getCredentialsFilePath());
        //TODO brf by Djer chargement d'un credential Externe au jar ?
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(configuration.getTokensDirectoryPath())))
                .setAccessType("offline")
                .build();
        return flow;
	}
}