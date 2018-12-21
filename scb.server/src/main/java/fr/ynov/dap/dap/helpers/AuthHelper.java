package fr.ynov.dap.dap.helpers;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.api.client.http.HttpHeaders;

import fr.ynov.dap.dap.Config;
import fr.ynov.dap.dap.models.TokenResponse;
import fr.ynov.dap.dap.services.microsoft.TokenService;

@Component
public class AuthHelper {
	
  @Autowired
  Config config;
  private String authority;
  private String authorizeUrl;

  private String[] scopes = { 
    "openid", 
    "offline_access",
    "profile", 
    "User.Read",
    "Mail.Read",
    "Calendars.Read",
    "Contacts.Read"
  };

  private String appId = null;
  private String appPassword = null;
  private String redirectUrl = null;

  private String getAppId() {
    if (appId == null) {
      try {
        loadConfig();
      } catch (Exception e) {
          //TODO scb by Djer |log4J| Une petite log ?
        return null;
      }
    }
    return appId;
  }
  
  private String getAppPassword() {
    if (appPassword == null) {
      try {
        loadConfig();
      } catch (Exception e) {
        //TODO scb by Djer |log4J| Une petite log ?
        return null;
      }
    }
    return appPassword;
  }

  private String getRedirectUrl() {
    if (redirectUrl == null) {
      try {
        loadConfig();
      } catch (Exception e) {
        //TODO scb by Djer |log4J| Une petite log ?
        return null;
      }
    }
    return redirectUrl;
  }

  private String getScopes() {
    StringBuilder sb = new StringBuilder();
    for (String scope: scopes) {
      sb.append(scope + " ");
    }
    return sb.toString().trim();
  }

  private void loadConfig() {
        appId = config.getAppId();
        appPassword = config.getAppPassword();
        redirectUrl = config.getRedirectUrl();
        authority = config.getAuthorize();
        authorizeUrl = authority + config.getAuthorizeUrl();
  }

  public String getLoginUrl(UUID state, UUID nonce) {
	try {
		loadConfig();
	}catch(Exception e) {
	  //TODO scb by Djer |log4J| Une petite log ? Est-ce prévu que loadConfig lève une Exception ?
		return null;
	}
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
  
  public TokenResponse getTokenFromAuthCode(String authCode, String tenantId) {
	  // Create a logging interceptor to log request and responses
      //TODO scb by Djer |Spring| Pour éviter de devoir appeler le "loadConfig" dans chaque méthode tupeux : soit l'appelr dans le constructeur ("pure JAVA") soit anoté ta méthode "loadConfig" avec @PostConstruct qui est une anotation  qui demande a Spring d'appler cette méthode une fois qu'il a fini de construire l'objet
	  loadConfig();
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
	    //TODO scb by Djer |log4J| Une petite log ?
	    TokenResponse error = new TokenResponse();
	    error.setError("IOException");
	    error.setErrorDescription(e.getMessage());
	    return error;
	  }
	}
  
  public TokenResponse ensureTokens(TokenResponse tokens, String tenantId) {
	  loadConfig();
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
	      //TODO scb by Djer |log4J| Une petite log ?
	      TokenResponse error = new TokenResponse();
	      error.setError("IOException");
	      error.setErrorDescription(e.getMessage());
	      return error;
	    }
	  }
	}
}