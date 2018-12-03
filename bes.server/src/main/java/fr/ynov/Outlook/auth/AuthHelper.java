package fr.ynov.Outlook.auth;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.UriComponentsBuilder;

import fr.ynov.dap.GmailPOO.metier.Data;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class AuthHelper {
	private static final String authority = "https://login.microsoftonline.com";
	private static final String authorizeUrl = authority + "/common/oauth2/v2.0/authorize";
	private static final Logger logger = LogManager.getLogger();
	private static String[] scopes = { 
		"openid", 
		"offline_access",
		"profile", 
		"User.Read",
		"Mail.Read",
		"Calendars.Read",
		"Contacts.Read"
	};

	private static String appId = null;
	private static String appPassword = null;
	private static String redirectUrl = null;
	
	private static String getAppId() {
		if (appId == null) {
			try {
				loadConfig();
			} catch (Exception e) {
				return null;
			}
		}
		return appId;
	}
	private static String getAppPassword() {
		if (appPassword == null) {
			try {
				loadConfig();
			} catch (Exception e) {
				return null;
			}
		}
		return appPassword;
	}
	
	private static String getRedirectUrl() {
		if (redirectUrl == null) {
			try {
				loadConfig();
			} catch (Exception e) {
				return null;
			}
		}
		return redirectUrl;
	}
	
	private static String getScopes() {
		StringBuilder sb = new StringBuilder();
		for (String scope: scopes) {
			sb.append(scope + " ");
		}
		return sb.toString().trim();
	}
	
	private static void loadConfig() throws IOException {
		String authConfigFile = "auth.properties";
		InputStream authConfigStream = AuthHelper.class.getClassLoader().getResourceAsStream(authConfigFile);
		
		if (authConfigStream != null) {
			Properties authProps = new Properties();
			try {
				authProps.load(authConfigStream);
				appId = authProps.getProperty("appId");
				appPassword = authProps.getProperty("appPassword");
				redirectUrl = authProps.getProperty("redirectUrl");
			} finally {
				authConfigStream.close();
			}
		}
		else {
			throw new FileNotFoundException("Property file '" + authConfigFile + "' not found in the classpath.");
		}
	}
	
	public static String getLoginUrl(UUID state, UUID nonce) {
		
		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(authorizeUrl);
		urlBuilder.queryParam("client_id", getAppId());
		urlBuilder.queryParam("redirect_uri", getRedirectUrl());
		urlBuilder.queryParam("response_type", "code id_token");
		urlBuilder.queryParam("scope", getScopes());
		urlBuilder.queryParam("state", state);
		urlBuilder.queryParam("nonce", nonce);
		urlBuilder.queryParam("response_mode", "form_post");
		
		return urlBuilder.toUriString();
	}
	
	public static TokenResponse getTokenFromAuthCode(String authCode, String tenantId) {
		// Create a logging interceptor to log request and responses
		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		
		OkHttpClient client = new OkHttpClient.Builder()
				.addInterceptor(interceptor).build();
		
		// Create and configure the Retrofit object
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(authority)
				.client(client)
				.addConverterFactory(JacksonConverterFactory.create())
				.build();
		
		// Generate the token service
		TokenService tokenService = retrofit.create(TokenService.class);
		
		try {
			return tokenService.getAccessTokenFromAuthCode(tenantId, getAppId(), getAppPassword(), 
					"authorization_code", authCode, getRedirectUrl()).execute().body();
			
		} catch (IOException e) {
			TokenResponse error = new TokenResponse();
			error.setError("IOException");
			error.setErrorDescription(e.getMessage());
			return error;
		}
	}
	 
	public static TokenResponse ensureTokens(TokenResponse tokens, String tenantId) {
		// Are tokens still valid?
	    //TODO bes by Djer |IDE| Ton IDE te dit que ce n'est pas utilisé (tu fait un "new Date() plus bas). Supprime cette variable ou utilise-la.
		Calendar now = Calendar.getInstance();
		if (new Date().before(tokens.getExpirationTime())) {
			// Still valid, return them as-is
			
		    //TODO bes by Djer |Log4J| C'est un cas normal, pas sur qu'une LOG en INFO soit nécéssaire. Debug (voir pas de log) serait suffisant
		    //TODO bes by Djer |LoJ| Contextualise tes logs (" for tenantId : " + tenantId)
			logger.info("Pas de changement jeton microsoft valide jusqu'au : "+tokens.getExpirationTime());
			return tokens;
		}
		else {
		    //TODO bes by Djer |Log4J| au lieu de ce commentaire de code une LOG en (info) serait tout aussi claire et en plus utile.
			// Expired, refresh the tokens
			// Create a logging interceptor to log request and responses
			HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
			interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
			
			OkHttpClient client = new OkHttpClient.Builder()
					.addInterceptor(interceptor).build();
			
			// Create and configure the Retrofit object
			Retrofit retrofit = new Retrofit.Builder()
					.baseUrl(authority)
					.client(client)
					.addConverterFactory(JacksonConverterFactory.create())
					.build();
			
			// Generate the token service
			TokenService tokenService = retrofit.create(TokenService.class);
			
			try {
				TokenResponse token=new TokenResponse();
					token =	tokenService.getAccessTokenFromRefreshToken(tenantId, getAppId(), getAppPassword(), 
						"refresh_token", tokens.getRefreshToken(), getRedirectUrl()).execute().body();
					//TODO bes by Djer |Log4J| Contextualise tes logs (" for tenantId : " + tenantId)
					logger.info("Nouveau jeton microsoft valide jusqu'au : "+tokens.getExpirationTime());
					token.setChanged(true);
					return token;
				
			} catch (IOException e) {
			  //TODO bes by Djer |Log4J| Contextualise tes logs (" for tenantId : " + tenantId). Et ajoute la cause en deuxième en paramètre.
				TokenResponse error = new TokenResponse();
				error.setError("IOException");
				error.setErrorDescription(e.getMessage());
				logger.error(error.getErrorDescription() );
				return error;
			}
		}
	}
}
