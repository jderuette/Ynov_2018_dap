package fr.ynov.dap.services.microsoft;

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

import fr.ynov.dap.web.microsoft.TokenService;

/**
 * @author alexa
 *
 */
public class AuthHelper {
  /**
   * adresse de l'entité d'autorité.
   */
  private static final String AUTHORITY = "https://login.microsoftonline.com";
  /**
   * Url autorisée.
   */
  private static final String AUTHORIZEURL = AUTHORITY + "/common/oauth2/v2.0/authorize";

  /**
   * scopes.
   */
  private static String[] scopes = {
    "openid",
    "offline_access",
    "profile",
    "User.Read",
    "Mail.Read",
    "Calendars.Read",
    "Contacts.Read"
  };

  /**
   * appId.
   */
  private static String appId = null;
  /**
   * appPassword.
   */
  private static String appPassword = null;
  /**
   * redirectURL.
   */
  private static String redirectUrl = null;

  /**
   * récupère l'id de l'API.
   * @return String appId.
   */
  private static String getAppId() {
    if (appId == null) {
      try {
        loadConfig();
      } catch (Exception e) {
          //TODO roa by Djer |Log4J| Une petite log ?
        return null;
      }
    }
    return appId;
  }
  /**
   * récupère le mot de passe de l'API.
   * @return String appPassword
   */
  private static String getAppPassword() {
    if (appPassword == null) {
      try {
        loadConfig();
      } catch (Exception e) {
        //TODO roa by Djer |Log4J| Une petite log ?
        return null;
      }
    }
    return appPassword;
  }
/**
 * récupère l'URL de redirection.
 * @return String redirectUrl.
 */
  private static String getRedirectUrl() {
    if (redirectUrl == null) {
      try {
        loadConfig();
      } catch (Exception e) {
        //TODO roa by Djer |Log4J| Une petite log ?
        return null;
      }
    }
    return redirectUrl;
  }
/**
 * récupère les scopes.
 * @return String scopes.
 */
  private static String getScopes() {
    StringBuilder sb = new StringBuilder();
    for (String scope: scopes) {
      sb.append(scope);
      //TODO roa by Djer |Audit Code| Ton outils d'audit de code t'indique que pour un caractère unique du devrait utiliser les simple quote. En effet la Java va créer une chaine de caractère avec un seul caractère ce qui n'est pas très efficace (mais pas très couteux non plus)
      sb.append(" ");
    }
    return sb.toString().trim();
  }
/**
 * charge la configuration.
 * @throws IOException problèe de lecture du fichier
 */
  private static void loadConfig() throws IOException {
    //TODO roa by Djer |Design Patern| Externalisation de la configuration ?
    String authConfigFile = "auth.properties";
    InputStream authConfigStream = AuthHelper.class.getClassLoader().getResourceAsStream(authConfigFile);

    if (authConfigStream != null) {
      Properties authProps = new Properties();
      try {
        authProps.load(authConfigStream);
        //TODO rao by Djer |Design Patern| Attention lorsque tu aura externalisé, défini une valeur par defaut et n'écrase que si pas "null" dans le fichier pour conserver le ZeroConf
        appId = authProps.getProperty("appId");
        appPassword = authProps.getProperty("appPassword");
        redirectUrl = authProps.getProperty("redirectUrl");
      } finally {
        authConfigStream.close();
      }
    } else {
      throw new FileNotFoundException("Property file '" + authConfigFile + "' not found in the classpath.");
    }
  }

  /**
   * récupère l'URL de connexion.
   * @param state state
   * @param nonce nonce
   * @return URL url de connexion
   */
  public static String getLoginUrl(final UUID state, final UUID nonce) {

    UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(AUTHORIZEURL);
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
   * récupère le token d'autentification du service Auth2 de microsoft.
   * @param authCode authCode
   * @param tenantId tenantId
   * @return TokenResponse
   */
  public static TokenResponse getTokenFromAuthCode(final String authCode, final String tenantId) {
    // Create a logging interceptor to log request and responses
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

    OkHttpClient client = new OkHttpClient.Builder()
        .addInterceptor(interceptor).build();

    // Create and configure the Retrofit object
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(AUTHORITY)
        .client(client)
        .addConverterFactory(JacksonConverterFactory.create())
        .build();

    // Generate the token service
    TokenService tokenService = retrofit.create(TokenService.class);

    try {
      return tokenService.getAccessTokenFromAuthCode(tenantId, getAppId(), getAppPassword(),
          "authorization_code", authCode, getRedirectUrl()).execute().body();
    } catch (IOException e) {
      //TODO roa by Djer |Log4J| Une petite log ?
      TokenResponse error = new TokenResponse();
      error.setError("IOException");
      error.setErrorDescription(e.getMessage());
      return error;
    }
  }
  /**
   * vérifie le token.
   * @param tokens tokens
   * @param tenantId tenanId
   * @return TokenResponse
   */
  public static TokenResponse ensureTokens(final TokenResponse tokens, final String tenantId) {
    // Are tokens still valid?
    Calendar now = Calendar.getInstance();
    if (now.getTime().before(tokens.getExpirationTime())) {
      // Still valid, return them as-is
      return tokens;
    } else {
      // Expired, refresh the tokens
      // Create a logging interceptor to log request and responses
      HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
      interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

      OkHttpClient client = new OkHttpClient.Builder()
          .addInterceptor(interceptor).build();

      // Create and configure the Retrofit object
      Retrofit retrofit = new Retrofit.Builder()
          .baseUrl(AUTHORITY)
          .client(client)
          .addConverterFactory(JacksonConverterFactory.create())
          .build();

      // Generate the token service
      TokenService tokenService = retrofit.create(TokenService.class);

      try {
        return tokenService.getAccessTokenFromRefreshToken(tenantId, getAppId(), getAppPassword(),
            "refresh_token", tokens.getRefreshToken(), getRedirectUrl()).execute().body();
      } catch (IOException e) {
        //TODO roa by Djer |Log4J| Une petite log ?
        TokenResponse error = new TokenResponse();
        error.setError("IOException");
        error.setErrorDescription(e.getMessage());
        return error;
      }
    }
  }
}
