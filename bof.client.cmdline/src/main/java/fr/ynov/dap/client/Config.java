package fr.ynov.dap.client;

//TODO bof by Djer |POO| Evite d'appeler cette classe "Config". Les constantes ne sont PAS configurables. Cela prete à confusion
public class Config {

	public final static String URL_BASE = "http://localhost:8080";
	public final static String ADD_ACCOUNT = "/account/add/";
	public final static String NB_UNREAD = "/mail/inbox?userID=";
	public final static String NB_CONTACT = "/contact/count?userID=";
	public final static String UPCOMMING_EVENT = "/calendar/upcoming?userID=";
	public static final String USER_AGENT = "Mozilla/5.0";
	
}
