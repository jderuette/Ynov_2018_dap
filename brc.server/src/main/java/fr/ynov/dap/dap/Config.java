package fr.ynov.dap.dap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.people.v1.PeopleServiceScopes;

/**
 * The Class Config.
 *
 * @author clement.brossette
 */

public class Config {
	
	/** The application name. */
	private String applicationName;
	
    
    /** The tokens directory path. */
    private String tokensDirectoryPath;
    
    /** The credentials file path. */
    private String credentialsFilePath;
    
    
    /**
     * Instantiates a new config.
     */
    public Config(){
    	this.applicationName = "Gmail API Java Quickstart";
    	this.tokensDirectoryPath  = "tokens";
    	this.credentialsFilePath  = "/credentials.json";
    }
    

	/**
     * Gets the application name.
     *
     * @return the application name
     */
    public final String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	
	/**
	 * Gets the tokens directory path.
	 *
	 * @return the tokens directory path
	 */
	public final String getTokensDirectoryPath() {
		return tokensDirectoryPath;
	}

	public void setTokensDirectoryPath(String tokensDirectoryPath) {
		this.tokensDirectoryPath = tokensDirectoryPath;
	}
	
	/**
	 * Credentials file path.
	 *
	 * @return the string
	 */
	public final String getCredentialsFilePath() {
		return credentialsFilePath;
	}

	public void setCredentialsFilePath(String credentialsFilePath) {
		this.credentialsFilePath = credentialsFilePath;
	}

	/**
	 * Gets the o auth 2 callback url.
	 *
	 * @return the o auth 2 callback url
	 */
	public String getoAuth2CallbackUrl() {
		// TODO Auto-generated method stub
		return "/oAuth2Callback";
	}
}
