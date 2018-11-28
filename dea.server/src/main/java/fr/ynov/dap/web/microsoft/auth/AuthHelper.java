
package fr.ynov.dap.web.microsoft.auth;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Properties;
import java.util.UUID;
import org.springframework.web.util.UriComponentsBuilder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


/**
 * Classe d'authentification générée par microsoft
 * 
 * @author antod
 *
 */
public class AuthHelper
{
  /**
   * Constante authority
   */
  private static final String authority = "https://login.microsoftonline.com";
  /**
   * Constante authorizeUrl
   */
  private static final String authorizeUrl = authority + "/common/oauth2/v2.0/authorize";

  /**
   * Variable des scopes microsoft
   */
  private static String[] scopes = { "openid", "offline_access", "profile", "User.Read", "Mail.Read", "Calendars.Read",
      "Contacts.Read" };

  /**
   * Variable appId
   */
  private static String appId = null;
  /**
   * Variable appPassword
   */
  private static String appPassword = null;
  /**
   * Variable redirectUrl
   */
  private static String redirectUrl = null;

  /**
   * Récupération de l'appId
   * 
   * @return
   */
  private static String getAppId()
  {
    if (appId == null)
    {
      try
      {
        loadConfig();
      } catch (Exception e)
      {
        return null;
      }
    }
    return appId;
  }

  /**
   * Récupération du appPassword
   * 
   * @return
   */
  private static String getAppPassword()
  {
    if (appPassword == null)
    {
      try
      {
        loadConfig();
      } catch (Exception e)
      {
        return null;
      }
    }
    return appPassword;
  }

  /**
   * Récupération du redirectUrl
   * 
   * @return
   */
  private static String getRedirectUrl()
  {
    if (redirectUrl == null)
    {
      try
      {
        loadConfig();
      } catch (Exception e)
      {
        return null;
      }
    }
    return redirectUrl;
  }

  /**
   * Récupération ds scopes
   * 
   * @return
   */
  private static String getScopes()
  {
    StringBuilder sb = new StringBuilder();
    for (String scope : scopes)
    {
      sb.append(scope + " ");
    }
    return sb.toString().trim();
  }

  /**
   * Chargement de la config
   * 
   * @throws IOException
   */
  private static void loadConfig() throws IOException
  {
    String authConfigFile = "auth.properties";
    InputStream authConfigStream = AuthHelper.class.getClassLoader().getResourceAsStream(authConfigFile);

    if (authConfigStream != null)
    {
      Properties authProps = new Properties();
      try
      {
        authProps.load(authConfigStream);
        appId = authProps.getProperty("appId");
        appPassword = authProps.getProperty("appPassword");
        redirectUrl = authProps.getProperty("redirectUrl");
      } finally
      {
        authConfigStream.close();
      }
    } else
    {
      throw new FileNotFoundException("Property file '" + authConfigFile + "' not found in the classpath.");
    }
  }

  /**
   * Récupération du loginUrl
   * 
   * @param state
   * @param nonce
   * @return
   */
  public static String getLoginUrl(UUID state, UUID nonce)
  {

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
   * Récupération du token de connexion
   * 
   * @param authCode
   * @param tenantId
   * @return
   */
  public static TokenResponse getTokenFromAuthCode(String authCode, String tenantId)
  {
    // Create a logging interceptor to log request and responses
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

    // Create and configure the Retrofit object
    Retrofit retrofit = new Retrofit.Builder().baseUrl(authority).client(client)
        .addConverterFactory(JacksonConverterFactory.create()).build();

    // Generate the token service
    TokenService tokenService = retrofit.create(TokenService.class);

    try
    {
      return tokenService.getAccessTokenFromAuthCode(tenantId, getAppId(), getAppPassword(), "authorization_code",
          authCode, getRedirectUrl()).execute().body();
    } catch (IOException e)
    {
      TokenResponse error = new TokenResponse();
      error.setError("IOException");
      error.setErrorDescription(e.getMessage());
      return error;
    }
  }

  /**
   * Méthode permettant de s'assurer de l'intégrité des tokens
   * 
   * @param tokens
   * @param tenantId
   * @return
   */
  public static TokenResponse ensureTokens(TokenResponse tokens, String tenantId)
  {
    // Are tokens still valid?
    Calendar now = Calendar.getInstance();
    if (now.getTime().before(tokens.getExpirationTime()))
    {
      // Still valid, return them as-is
      return tokens;
    } else
    {
      // Expired, refresh the tokens
      // Create a logging interceptor to log request and responses
      HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
      interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

      OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

      // Create and configure the Retrofit object
      Retrofit retrofit = new Retrofit.Builder().baseUrl(authority).client(client)
          .addConverterFactory(JacksonConverterFactory.create()).build();

      // Generate the token service
      TokenService tokenService = retrofit.create(TokenService.class);

      try
      {
        return tokenService.getAccessTokenFromRefreshToken(tenantId, getAppId(), getAppPassword(), "refresh_token",
            tokens.getRefreshToken(), getRedirectUrl()).execute().body();
      } catch (IOException e)
      {
        TokenResponse error = new TokenResponse();
        error.setError("IOException");
        error.setErrorDescription(e.getMessage());
        return error;
      }
    }
  }
}
