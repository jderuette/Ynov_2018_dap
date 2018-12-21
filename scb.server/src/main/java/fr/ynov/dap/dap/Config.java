package fr.ynov.dap.dap;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.people.v1.PeopleServiceScopes;

import fr.ynov.dap.dap.helpers.AuthHelper;


@Configuration
@PropertySource("classpath:application.properties")
public class Config{
	
	@Value("${app.credentialpath}")
    String credentialPath;
	@Value("${tokenPath}")
    String tokenPath;
	@Value("${appName}")
    String appName;
    @Value("${appId}")
	String appId;
    //TODO scb by Djer |POO| Précise que c'est le "microsoft" password (nom de l'attribut et/ou nom de la propriété)
	@Value("${appPassword}")
	String appPassword;
	@Value("${redirectUrl}")
	String redirectUrl;
	@Value("${authority}")
	String authorize;
	@Value("${authorizeUrl}")
	String authorizeUrl;
	
	//TODO scb by Djer |POO| Comme dnas uen classe "commune" devrait être renommer en "allGoogleScropes" pour être plus claire
	public List<String> allScopes = new ArrayList<String>(Arrays.asList(GmailScopes.GMAIL_LABELS, CalendarScopes.CALENDAR_READONLY, PeopleServiceScopes.CONTACTS_READONLY));
	
	//TODO scb by Djer |POO| Evite de mélanger des getter/setter au milieu de tes attributs
	public String getoAuth2CallbackUrl() {
		return "/oAuth2CallBack";
	}
	
	
	public int sensibleDataLastChar = 0;
	public int sensibleDataFirstChar = 0;
	
	public String getCredentialPath() {
	    //TODO scb by Djer |Design Patern| Doamge que se "dossier racine" ne soit pas configurable
		String path = System.getProperty("user.home") + System.getProperty("file.separator");
		
		return path  + credentialPath;
	}
	public void setCredentialPath(String credentialPath) {
		this.credentialPath = credentialPath;
	}
	public String getTokenPath() {
		return tokenPath;
	}
	public void setTokenPath(String tokenPath) {
	    //TODO scb by Djer |Design Patern| Devrait être relatif au "dossier racine" comme le credentialPath ?
		this.tokenPath = tokenPath;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public List<String> getAllScopes() {
		return allScopes;
	}
	public void setAllScopes(List<String> allScopes) {
		this.allScopes = allScopes;
	}
	public int getSensibleDataLastChar() {
		return sensibleDataLastChar;
	}
	public void setSensibleDataLastChar(int sensibleDataLastChar) {
		this.sensibleDataLastChar = sensibleDataLastChar;
	}
	public int getSensibleDataFirstChar() {
		return sensibleDataFirstChar;
	}
	public void setSensibleDataFirstChar(int sensibleDataFirstChar) {
		this.sensibleDataFirstChar = sensibleDataFirstChar;
	}
	public String getAppId() {
		return appId;
	}
	public String getAppPassword() {
		return appPassword;
	}
	public String getRedirectUrl() {
		return redirectUrl;
	}
	public String getAuthorize() {
		return authorize;
	}
	public String getAuthorizeUrl() {
		return authorizeUrl;
	}
	
	
	
}