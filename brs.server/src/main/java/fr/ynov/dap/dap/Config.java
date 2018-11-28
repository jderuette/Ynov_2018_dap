package fr.ynov.dap.dap;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.people.v1.PeopleServiceScopes;

/**
 * The Class config.
 */
public class Config {

	/** The application name. */
	private final String APPLICATION_NAME = "DAP";

	/** The credentials file path. */
	String homePath = System.getProperty("user.home");
	String path = homePath+ File.separator +"dap/cred.json";
	
	private String authority = "https://login.microsoftonline.com";
	  

	private String authorizeUrl = authority + "/common/oauth2/v2.0/authorize";
	private final String CREDENTIALS_FILE_PATH = homePath+ File.separator +"dap/credentials.json";

	/** The tokens directory path. */
	
	private final String TOKENS_DIRECTORY_PATH = "tokens";
	
	/** The json factory. */
	
	/** The Server call back. */
	
	private final String ServerCallBack = "/oAuth2Callback";
	

	

	 	private String applicationName;
		private String credentialsFilePath;
	    private String tokensDirectoryPath;
	
	public Config() {
		this.applicationName = APPLICATION_NAME;
		this.credentialsFilePath = CREDENTIALS_FILE_PATH;
		this.tokensDirectoryPath = TOKENS_DIRECTORY_PATH;
		
	}


	public Config(String app_name, String token_path, String credentials, String authrizeMicrosoftUrl ) {
		this.applicationName = app_name;
		this.credentialsFilePath = credentials;
		this.tokensDirectoryPath = token_path;
		this.authorizeUrl = authrizeMicrosoftUrl;
	}


	
	

	public String getAuthority() {
		return authority;
	}


	public void setAuthority(String authority) {
		this.authority = authority;
	}


	public String getAuthorizeUrl() {
		return authorizeUrl;
	}


	public void setAuthorizeUrl(String authorizeUrl) {
		this.authorizeUrl = authorizeUrl;
	}
	public String getoAuth2CallbackUrl() {
	    
		return ServerCallBack;
	}
	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getCredentialsFilePath() {
		return credentialsFilePath;
	}

	public void setCredentialsFilePath(String credentialsFilePath) {
		this.credentialsFilePath = credentialsFilePath;
	}

	public String getTokensDirectoryPath() {
		return tokensDirectoryPath;
	}

	public void setTokensDirectoryPath(String tokensDirectoryPath) {
		this.tokensDirectoryPath = tokensDirectoryPath;
	}
}