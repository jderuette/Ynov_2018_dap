package fr.ynov.dap.microsoft.service;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import fr.ynov.dap.microsoft.data.MicrosoftAccount;
import fr.ynov.dap.microsoft.data.TokenResponse;
import fr.ynov.dap.microsoft.models.Contact;
import fr.ynov.dap.microsoft.models.Event;
import fr.ynov.dap.microsoft.models.Message;
import fr.ynov.dap.microsoft.models.OutlookFolder;
import fr.ynov.dap.microsoft.models.PagedResult;
import fr.ynov.dap.models.AccountMailResponse;
import fr.ynov.dap.models.NbContactResponse;
import fr.ynov.dap.google.data.AppUser;
import fr.ynov.dap.microsoft.contract.OutlookApiService;
import fr.ynov.dap.microsoft.contract.OutlookServiceBuilder;


/**
 * The Class OutlookService.
 */
@Service
public class OutlookService {
	
	/** The logger. */
	private final static Logger logger = LogManager.getLogger(OutlookService.class);

	/** The logger message no ms accounts. */
	private final String LOGGER_MESSAGE_NO_MS_ACCOUNTS = "no microsoft account found for userkey : ";
	
	/** The logger message ms accounts missing. */
	private final String LOGGER_MESSAGE_MS_ACCOUNTS_MISSING = "one microsoft account account is missing";
	
	/** The logger message attribute missing. */
	private final String LOGGER_MESSAGE_ATTRIBUTE_MISSING = "one account attribute is missing";

	/**
	 * Gets the inbox mail for account.
	 *
	 * @param user the user
	 * @return the inbox mail for account
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public ArrayList<AccountMailResponse> getInboxMailForAccount(final AppUser user) throws IOException {
		if (user.getMicrosoftAccounts().size() == 0) {
        	logger.info(LOGGER_MESSAGE_NO_MS_ACCOUNTS + user.getUserKey());
            return null;
        }
		
		ArrayList<AccountMailResponse> AllAccountMessages = new ArrayList<AccountMailResponse>();
		
		for (MicrosoftAccount account : user.getMicrosoftAccounts()) {
			AccountMailResponse accountMailResponse = new AccountMailResponse();
			accountMailResponse.setName(account.getName());
			
			ArrayList<Message> mails = new ArrayList<Message>();
            for(Message message : this.getInboxMail(account)) {
            	mails.add(message);
            }
            accountMailResponse.setMails(mails);
            AllAccountMessages.add(accountMailResponse);
        }
		
		return AllAccountMessages;
	}
	
    /**
     * Gets the nb unread emails for account.
     *
     * @param user the user
     * @return the nb unread emails for account
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public final Integer getNbUnreadEmailsForAccount(final AppUser user) throws IOException {

        if (user.getMicrosoftAccounts().size() == 0) {
        	logger.info(LOGGER_MESSAGE_NO_MS_ACCOUNTS + user.getUserKey());
            return 0;
        }

        Integer numberOfUnreadMails = 0;

        for (MicrosoftAccount account : user.getMicrosoftAccounts()) {
            numberOfUnreadMails += getNbUnreadEmails(account);
        }
        return numberOfUnreadMails;
    }
    
    /**
     * Gets the next events for account.
     *
     * @param user the user
     * @return the next events for account
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public final Event getNextEventsForAccount(final AppUser user) throws IOException {

        if (user.getMicrosoftAccounts().size() == 0) {
        	logger.info(LOGGER_MESSAGE_NO_MS_ACCOUNTS + user.getUserKey());
            return null;
        }

        ArrayList<Event> events = new ArrayList<>();
;

        for (MicrosoftAccount account : user.getMicrosoftAccounts()) {
        	events.add(this.getNextEvent(account));
        }
        
        Event MsNextEvent = null;
		if(events.get(0) != null) {
			MsNextEvent = events.get(0);
			for(int i=0; i<events.size(); i++) {
				if(events.size()>1 && events.get(i).getStart().getDateTime().before(MsNextEvent.getStart().getDateTime())) {	
					MsNextEvent = events.get(i);
				}	
			}
		}
        
        return MsNextEvent;
    }
    
    /**
     * Gets the nb contact for account.
     *
     * @param user the user
     * @return the nb contact for account
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public NbContactResponse getNbContactForAccount(final AppUser user) throws IOException {
    	
    	if (user.getMicrosoftAccounts().size() == 0) {
        	logger.info(LOGGER_MESSAGE_NO_MS_ACCOUNTS + user.getUserKey());
            return null;
        }
    	
    	Integer totalNbContact = 0;
    	
    	for (MicrosoftAccount account : user.getMicrosoftAccounts()) {
        	totalNbContact += this.getNbContact(account);
        }
    	 
    	return new NbContactResponse(totalNbContact);
    }
    
    /**
     * Gets the contact for account.
     *
     * @param user the user
     * @return the contact for account
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public ArrayList<Contact> getContactForAccount(final AppUser user) throws IOException {
    	
    	if (user.getMicrosoftAccounts().size() == 0) {
        	logger.info(LOGGER_MESSAGE_NO_MS_ACCOUNTS + user.getUserKey());
            return null;
        }
    	    	
    	ArrayList<Contact> contacts = new ArrayList<Contact>();
    	
    	for (MicrosoftAccount account : user.getMicrosoftAccounts()) {
    		for(Contact contact : this.getContact(account).getValue()) {
        		contacts.add(contact);
    		}
        }
    	return contacts;
    }
    
    /**
     * Gets the contact.
     *
     * @param msAcc the ms acc
     * @return the contact
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private PagedResult<Contact> getContact(MicrosoftAccount msAcc) throws IOException{
    	
    	if (msAcc == null) {
        	logger.info(LOGGER_MESSAGE_MS_ACCOUNTS_MISSING);
            return null;
        }
    	
    	String email = msAcc.getEmail();
        String tenantId = msAcc.getTenantId();
        TokenResponse tokens = msAcc.getToken();
        
        if (tenantId == null || email == null || tokens == null) {
        	logger.info(LOGGER_MESSAGE_MS_ACCOUNTS_MISSING);
            return null;
        }
        
        OutlookApiService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

     // Sort by given name in ascending order (A-Z)
        String sort = "GivenName ASC";
        // Only return the properties we care about
        String properties = "GivenName,Surname,CompanyName,EmailAddresses";
        
        PagedResult<Contact> contacts = outlookService.getAllContacts(
                sort, properties).execute().body();
        
        if(contacts == null) {
        	return null;
        }
        
        return contacts;
    }
    
    /**
     * Gets the nb contact.
     *
     * @param msAcc the ms acc
     * @return the nb contact
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private Integer getNbContact(MicrosoftAccount msAcc) throws IOException {
    	
    	if (msAcc == null) {
        	logger.info(LOGGER_MESSAGE_MS_ACCOUNTS_MISSING);
            return 0;
        }
    	
    	String email = msAcc.getEmail();
        String tenantId = msAcc.getTenantId();
        TokenResponse tokens = msAcc.getToken();

        if (tenantId == null || email == null || tokens == null) {
        	logger.info(LOGGER_MESSAGE_ATTRIBUTE_MISSING);
            return null;
        }
        
        OutlookApiService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

        // Sort by given name in ascending order (A-Z)
        String sort = "GivenName ASC";
        // Only return the properties we care about
        String properties = "GivenName,Surname,CompanyName,EmailAddresses";
        
        PagedResult<Contact> contacts = outlookService.getAllContacts(
                sort, properties).execute().body();
        
        if(contacts == null) {
        	return 0;
        }
        
        return contacts.getValue().length;
    }
    
    /**
     * Gets the next event.
     *
     * @param msAcc the ms acc
     * @return the next event
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private Event getNextEvent(final MicrosoftAccount msAcc) throws IOException {

        if (msAcc == null) {
        	logger.info(LOGGER_MESSAGE_MS_ACCOUNTS_MISSING);
            return null;
        }

        String email = msAcc.getEmail();
        String tenantId = msAcc.getTenantId();
        TokenResponse tokens = msAcc.getToken();

        if (tenantId == null || email == null || tokens == null) {
        	logger.info(LOGGER_MESSAGE_ATTRIBUTE_MISSING);
            return null;
        }

        OutlookApiService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

        String filter = "start/dateTime ge '" + Instant.now().toString() + "'";
        // Sort by start time in descending order
        String sort = "start/dateTime ASC";
        // Only return the properties we care about
        String properties = "organizer,subject,start,end";
        // Return at most 1 event
        Integer maxResults = 1;
        
        PagedResult<Event> events = outlookService.getEvents(
                sort, filter, properties, maxResults)
                .execute().body();
        logger.info("events : " + events);
        return events.getValue()[0];
    }
    
    /**
     * Gets the nb unread emails.
     *
     * @param msAcc the ms acc
     * @return the nb unread emails
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private Integer getNbUnreadEmails(final MicrosoftAccount msAcc) throws IOException {

        if (msAcc == null) {
        	logger.info(LOGGER_MESSAGE_MS_ACCOUNTS_MISSING);
            return 0;
        }

        String email = msAcc.getEmail();
        String tenantId = msAcc.getTenantId();
        TokenResponse tokens = msAcc.getToken();

        if (tenantId == null || email == null || tokens == null) {
        	logger.info(LOGGER_MESSAGE_ATTRIBUTE_MISSING);
            return 0;
        }

        OutlookApiService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

        OutlookFolder inboxFolder = outlookService.getFolder("inbox").execute().body();

        if (inboxFolder == null) {
        	logger.info("folder inbox not found");
            return 0;
        }

        return inboxFolder.getUnreadItemCount();

    }
    
    /**
     * Gets the inbox mail.
     *
     * @param msAcc the ms acc
     * @return the inbox mail
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private Message[] getInboxMail(final MicrosoftAccount msAcc) throws IOException {
		if (msAcc == null) {
        	logger.info(LOGGER_MESSAGE_MS_ACCOUNTS_MISSING);
            return null;
        }

        String email = msAcc.getEmail();
        String tenantId = msAcc.getTenantId();
        TokenResponse tokens = msAcc.getToken();
        

        if (tenantId == null || email == null || tokens == null) {
        	logger.info(LOGGER_MESSAGE_ATTRIBUTE_MISSING);
            return null;
        }
        
        // Retrieve messages from the inbox
        String folder = "inbox";
        // Sort by time received in descending order
        String sort = "receivedDateTime DESC";
        // Only return the properties we care about
        String properties = "receivedDateTime,from,isRead,subject,bodyPreview";
        // Return at most 10 messages
        Integer maxResults = 10;

        OutlookApiService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

        PagedResult<Message> messages = outlookService.getMessages(
                folder, sort, properties, maxResults)
                .execute().body();

        return messages.getValue();
	}
    
    
}
