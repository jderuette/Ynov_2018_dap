package fr.ynov.dap.dap.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Service;
import com.google.api.services.gmail.Gmail;

/**
 * 
 * @author Florian BRANCHEREAU
 * Extends MainService
 */
@Service
public class GmailService extends GoogleService {
	
	public GmailService() throws Exception, IOException {

	}

	/**
	 * 
	 * @return gmailservice
	 * @throws IOException
	 * @throws GeneralSecurityException 
	 */
	private Gmail BuildGmailService(String userKey) throws IOException, GeneralSecurityException
	{
		Gmail gmailservice = new Gmail.Builder(GetHttpTransport(), GetJsonFactory(), getCredentials(userKey))
	            .setApplicationName(configuration.getApplicationName())
	            .build();
		return gmailservice;
	}
	
	/**
     * 
     * @return GetServiceGmail : Le nombre de mail non lu
	 * @throws Exception 
     */
    public int getMsgsUnread(String userKey) throws Exception
    {
    	return this.GetServiceGmail(userKey).users().labels().get("me", "INBOX").execute().getMessagesUnread();
    }
    
	/**
	 * 
	 * @return BuildGmailService
	 * @throws IOException 
	 * @throws GeneralSecurityException 
	 */
	public Gmail GetServiceGmail(String userKey) throws IOException, GeneralSecurityException
	{
		return BuildGmailService(userKey);
	}

}
