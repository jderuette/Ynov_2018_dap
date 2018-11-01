package fr.ynov.dap.dap.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import org.springframework.stereotype.Service;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;

/**
 * 
 * @author Gaël BOSSER
 * Manage the redirection to the Controller
 * Extends MainService
 */
//TODO bog by Djer CF remarques de brf (FLorian) car même code
@Service
public class GmailService extends GoogleService {
	/**
	 * Constructor GmailService
	 * @throws Exception
	 * @throws IOException
	 */
	public GmailService() throws Exception, IOException {

	}
	/**
	 * 
	 * @return Service Gmail
	 * @throws IOException 
	 * @throws GeneralSecurityException 
	 */
	public Gmail GetServiceGmail(String userId) throws IOException, GeneralSecurityException
	{
		return BuildGmailService(userId);
	}
	/**
	 * 
	 * @param userId
	 * userId parameter put by client
	 * @return Gmail service
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	private Gmail BuildGmailService(String userId) throws IOException, GeneralSecurityException
	{
		Gmail service = new Gmail.Builder(GetHttpTransport(), GetJsonFactory(), getCredentials(userId))
	            .setApplicationName(configuration.getApplicationName())
	            .build();
		return service;
	}
	
	/**
	 * 
	 * @param userId
	 * userId parameter put by client
	 * @return int numbers of unread mails
	 * @throws Exception
	 */
    public int getMsgsUnread(String userId) throws Exception
    {
    	return this.GetServiceGmail(userId).users().labels().get("me", "INBOX").execute().getMessagesUnread();
    }
    
    /**
     * 
     * @param userId
     * userId parameter put by client
     * @return List of Label
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public List<Label> GetListLabelsGmail(String userId) throws IOException, GeneralSecurityException
    {
    	return this.GetServiceGmail(userId).users().labels().list(userId).execute().getLabels();
    }
}