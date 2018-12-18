package fr.ynov.dap.dap.web;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.FileDataStoreFactory;

import fr.ynov.dap.dap.App;
import fr.ynov.dap.dap.data.Token;

@RestController
public class PageCredentials {

	@RequestMapping("/credentials")
	public String welcome(ModelMap model, HttpServletRequest request) throws GeneralSecurityException, IOException
	{
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		InputStreamReader inputStrReader = new InputStreamReader(new FileInputStream(App.config.getCredentialsFilePath()), Charset.forName("UTF-8"));
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(App.config.GetJsonFactory(), inputStrReader);
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
	            HTTP_TRANSPORT, App.config.GetJsonFactory(), clientSecrets, App.config.getScopes())
	            .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(App.config.GetTokensDirectoryPath())))
	            .setAccessType("offline")
	            .build();
		  
		DataStore<StoredCredential> credentials = flow.getCredentialDataStore();	
		
		model.addAttribute("credentials",credentials);
		
		//TODO for by Djer |API Microsoft| Credentials Microsoft ?
		return credentials.toString();
	}
}
