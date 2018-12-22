package fr.ynov.dap;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Properties;
import java.util.UUID;

import org.springframework.web.util.UriComponentsBuilder;

import fr.ynov.dap.service.MicrosoftTokenResponseService;
import fr.ynov.dap.service.MicrosoftTokenService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Permet de déclarer les scopes et gérer la config microsoft
 * @author abaracas
 *
 */
public class AuthHelper {
	private static final String authority = "https://login.microsoftonline.com";
	private static final String authorizeUrl = authority + "/common/oauth2/v2.0/authorize";
	private static String[] scopes = { 
		"openid", 
		"offline_access",
		"profile", 
		"https://outlook.office.com/mail.read",
		"https://outlook.office.com/calendars.read",
		"https://outlook.office.com/contacts.read"
	};
	
	private static String appId = null;
	private static String appPassword = null;
	private static String redirectUrl = null;
	
	private static String getAppId() {
		if (appId == null) {
			try {
				loadConfig();
			} catch (Exception e) {
			  //TODO baa by Djer |Log4J| Une petite log ?
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
			  //TODO baa by Djer |Log4J| Une petite log ?
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
			  //TODO baa by Djer |Log4J| Une petite log ?
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
	  //TODO baa by Djer |Design Patern| Externalisation de la configuration ?
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
	/**
	 * Recupère l'URL de login microsoft
	 * @param state etat
	 * @param nonce aucun
	 * @return l'url concernée
	 */
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
	/**
	 * Genère le TokenService via le tenantId et le authCode
	 * @param authCode le code d'authentification
	 * @param tenantId le tenant id lié au compte
	 * @return le token
	 */
	public static MicrosoftTokenResponseService getTokenFromAuthCode(String authCode, String tenantId) {
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
		MicrosoftTokenService tokenService = retrofit.create(MicrosoftTokenService.class);
		
		try {
			return tokenService.getAccessTokenFromAuthCode(tenantId, getAppId(), getAppPassword(), 
					"authorization_code", authCode, getRedirectUrl()).execute().body();
		} catch (IOException e) {
		  //TODO baa by Djer |Log4J| Une petite log ?
			MicrosoftTokenResponseService error = new MicrosoftTokenResponseService();
			error.setError("IOException");
			error.setErrorDescription(e.getMessage());
			return error;
		}
	}
	 /**
	  * vérifie la validité du token microsoft, si valide le récupère, sinon en recrée un
	  * @param tokens le token
	  * @param tenantId le tenant id lié
	  * @return le token
	  */
	public static MicrosoftTokenResponseService ensureTokens(MicrosoftTokenResponseService tokens, String tenantId) {
		// Are tokens still valid?
		Calendar now = Calendar.getInstance();
		if (now.getTime().after(tokens.getExpirationTime())) {
			// Still valid, return them as-is
			return tokens;
		}
		else {
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
			MicrosoftTokenService tokenService = retrofit.create(MicrosoftTokenService.class);
			
			try {
				return tokenService.getAccessTokenFromRefreshToken(tenantId, getAppId(), getAppPassword(), 
						"refresh_token", tokens.getRefreshToken(), getRedirectUrl()).execute().body();
			} catch (IOException e) {
			  //TODO baa by Djer |Log4J| Une petite log ?
				MicrosoftTokenResponseService error = new MicrosoftTokenResponseService();
				error.setError("IOException");
				error.setErrorDescription(e.getMessage());
				return error;
			}
		}
	}
}
