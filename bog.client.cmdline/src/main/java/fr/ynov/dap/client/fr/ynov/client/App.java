package fr.ynov.dap.client.fr.ynov.client;

import java.io.IOException;
import java.net.URISyntaxException;

import fr.ynov.dap.client.fr.ynov.client.Api.AccountApi;
import fr.ynov.dap.client.fr.ynov.client.Api.CalendarApi;
import fr.ynov.dap.client.fr.ynov.client.Api.GmailApi;
import fr.ynov.dap.client.fr.ynov.client.Api.PeopleApi;

/**
 * 
 * @author Mon_PC
 * Point d'entrée de l'application client
 */
public class App 
{
	private static GmailApi gmailRessource;
	private static CalendarApi calendarRessource;
	private static PeopleApi peopleRessource;
	private static AccountApi accountRessource;
	
	private static String responseBodyEmail;
	private static String responseBodyCalendar;
	private static String responseBodyPeople;
	private static String responseAddAccount;
	
	/**
	 * 
	 * @param String[] args
	 * args contains every params typed in command line
	 * @throws IOException
	 * @throws URISyntaxException
	 */
    public static void main( String[] args ) throws IOException, URISyntaxException
    {
    	gmailRessource = new GmailApi();
    	calendarRessource = new CalendarApi();
    	peopleRessource = new PeopleApi();
    	accountRessource = new AccountApi();
  	
    	if(args.length > 0 && args.length < 3)
    	{
            try
            {
            	String action = "";
            	String userKey = "";
            	if(args.length == 2)
            	{
            		action = args[0].toLowerCase();
            		userKey = args[1];
            	}
            	else
            	{
            		userKey = args[0];
            	}
            	
            	if(action.equals("view") || action.equals(""))
            	{
            		responseBodyEmail = gmailRessource.GetEmailNonLus(userKey);
                	responseBodyCalendar = calendarRessource.GetNextEvent(userKey);
                	responseBodyPeople = peopleRessource.GetNbContacts(userKey);
                	
                	System.out.println("Mails non lus : " + responseBodyEmail);
                	System.out.println(responseBodyCalendar);
                	System.out.println("Vous avez : " + responseBodyPeople + " contact(s) pour ce compte");
            	}
            	else
            	{
            		if(action.equals("add"))
            		{
            			responseAddAccount = accountRessource.CreateAccount(userKey);
            			if(responseAddAccount == null)
            			{
            				System.out.println("Ajout du client...");
            			}
            			else
            			{
            				System.out.println(responseAddAccount);
            			}
            		}
            	}
            }
            catch (StringIndexOutOfBoundsException e)
            {
                System.err.println("Action ou paramètre invalide");
            }
    	}
    	else
    	{
    		System.err.println("Erreur: 1 action et 1 paramètre maximum !");
    	}
    }
}
