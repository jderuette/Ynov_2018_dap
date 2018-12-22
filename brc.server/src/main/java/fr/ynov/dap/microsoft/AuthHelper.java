package fr.ynov.dap.microsoft;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Properties;
import java.util.UUID;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import org.springframework.web.util.UriComponentsBuilder;

import fr.ynov.dap.microsoft.contract.TokenService;
import fr.ynov.dap.microsoft.data.TokenResponse;

/**
 * The Class AuthHelper.
 */
public class AuthHelper {
  
  /** The Constant authority. */
  private static final String authority = "https://login.microsoftonline.com";
  
  /** The Constant authorizeUrl. */
  private static final String authorizeUrl = authority + "/common/oauth2/v2.0/authorize";

	/** The scopes. */
	private static String[] scopes = { "openid", "offline_access", "profile", "User.Read", "Mail.Read",
			"Calendars.Read", "Contacts.Read" };

  /** The app id. */
  private static String appId = null;
  
  /** The app password. */
  private static String appPassword = null;
  
  /** The redirect url. */
  private static String redirectUrl = null;

  /**
   * Gets the app id.
   *
   * @return the app id
   */
  private static String getAppId() {
    if (appId == null) {
      try {
        loadConfig();
      } catch (Exception e) {
          //TODO brc by Djer |Log4J| Une petite log ?
          //TODO brc by Djer |POO| Evite les multiple return dans une même méthode
        return null;
      }
    }
    return appId;
  }
  
  /**
   * Gets the app password.
   *
   * @return the app password
   */
  private static String getAppPassword() {
    if (appPassword == null) {
      try {
        loadConfig();
      } catch (Exception e) {
        //TODO brc by Djer |Log4J| Une petite log ?
          //TODO brc by Djer |POO| Evite les multiple return dans une même méthode
        return null;
      }
    }
    return appPassword;
  }

  /**
   * Gets the redirect url.
   *
   * @return the redirect url
   */
  private static String getRedirectUrl() {
    if (redirectUrl == null) {
      try {
        loadConfig();
      } catch (Exception e) {
        //TODO brc by Djer |Log4J| Une petite log ?
          //TODO brc by Djer |POO| Evite les multiple return dans une même méthode
        return null;
      }
    }
    return redirectUrl;
  }

  /**
   * Gets the scopes.
   *
   * @return the scopes
   */
  private static String getScopes() {
    StringBuilder sb = new StringBuilder();
    for (String scope: scopes) {
      sb.append(scope + " ");
    }
    return sb.toString().trim();
  }

  /**
   * Load config.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private static void loadConfig() throws IOException {
      //TODO brc by Djer |Design Patern| Externalisation de la configuration ?
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
   * Gets the login url.
   *
   * @param state the state
   * @param nonce the nonce
   * @return the login url
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
   * Gets the token from auth code.
   *
   * @param authCode the auth code
   * @param tenantId the tenant id
   * @return the token from auth code
   */
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
	    //TODO brc by Djer |Log4J| Une petite log ?
	    TokenResponse error = new TokenResponse();
	    error.setError("IOException");
	    error.setErrorDescription(e.getMessage());
	    return error;
	  }
	}
  
  /**
   * Ensure tokens.
   *
   * @param tokens the tokens
   * @param tenantId the tenant id
   * @return the token response
   */
  public static TokenResponse ensureTokens(TokenResponse tokens, String tenantId) {
	  // Are tokens still valid?
	  Calendar now = Calendar.getInstance();
	  if (now.getTime().before(tokens.getExpirationTime())) {
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
	    TokenService tokenService = retrofit.create(TokenService.class);

	    try {
	      return tokenService.getAccessTokenFromRefreshToken(tenantId, getAppId(), getAppPassword(), 
	          "refresh_token", tokens.getRefreshToken(), getRedirectUrl()).execute().body();
	    } catch (IOException e) {
	      //TODO brc by Djer |Log4J| Une petite log ?
	      TokenResponse error = new TokenResponse();
	      error.setError("IOException");
	      error.setErrorDescription(e.getMessage());
	      return error;
	    }
	  }
	}
}