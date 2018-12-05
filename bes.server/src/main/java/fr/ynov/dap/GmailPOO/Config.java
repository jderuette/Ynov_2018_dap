package fr.ynov.dap.GmailPOO;



import java.util.ArrayList;
import java.util.Arrays;



import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.people.v1.PeopleServiceScopes;


public class Config {
	private  final String CREDENTIALS_FILE = "/credentials.json";
	private  final String CLIENT_SECRET_DIR = "tokens";
	private  final String APPLICATION_NAME = "HoCDaP";
	private  final String SCOOPLIST[] = { GmailScopes.GMAIL_READONLY, CalendarScopes.CALENDAR,PeopleServiceScopes.CONTACTS_READONLY };
	
	private  java.util.List<String> allSCOPES;
	public String applicationName;
	public String credentialFolder;
	public String clientSecretFile;
	
	 public Config (){
           this.applicationName=APPLICATION_NAME;
           this.clientSecretFile=CREDENTIALS_FILE;
           this.credentialFolder=CLIENT_SECRET_DIR;
           this.allSCOPES=new ArrayList(Arrays.asList(SCOOPLIST));
	}

	 //TODO bes by Djer |POO| implémentation un peu "étrange". Deplus n'est pas utilisé. A supprimer ! 
	public String getPath() 
	{
		return credentialFolder+"\\StoredCredential";
	}

	/**
	 * @return the allSCOPES
	 */
	public java.util.List<String> getAllSCOPES() {
		return allSCOPES;
	}



	/**
	 * @param allSCOPES the allSCOPES to set
	 */
	public void setAllSCOPES(java.util.List<String> allSCOPES) {
		this.allSCOPES = allSCOPES;
	}



	/**
	 * @return the applicationName
	 */
	public String getApplicationName() {
		return applicationName;
	}

	/**
	 * @param applicationName the applicationName to set
	 */
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	/**
	 * @return the credentialFolder
	 */
	public String getCredentialFolder() {
		return credentialFolder;
	}

	/**
	 * @param credentialFolder the credentialFolder to set
	 */
	public void setCredentialFolder(String credentialFolder) {
		this.credentialFolder = credentialFolder;
	}

	/**
	 * @return the clientSecretFile
	 */
	public String getClientSecretFile() {
		return clientSecretFile;
	}

	/**
	 * @param clientSecretFile the clientSecretFile to set
	 */
	public void setClientSecretFile(String clientSecretFile) {
		this.clientSecretFile = clientSecretFile;
	}

	public String getoAuth2CallbackUrl() {
	    //TODO bes by Djer |IDE| Supprime ce TO-DO s'il n'est plus juste. Ou alors profites-en pour faire du "zéroConf" sur cet élement.
		// TODO Auto-generated method stub
	
		return "http://localhost:8080/Callback";
	}
	public String getMapping() {
	  //TODO bes by Djer |IDE| Supprime ce TO-DO s'il n'est plus juste. Ou alors profites-en pour faire du "zéroConf" sur cet élement.
		// TODO Auto-generated method stub
	
		return "/Callback";
	}
	
}