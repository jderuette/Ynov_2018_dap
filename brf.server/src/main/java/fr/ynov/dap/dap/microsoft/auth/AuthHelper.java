package fr.ynov.dap.dap.microsoft.auth;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Properties;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.util.UriComponentsBuilder;

import fr.ynov.dap.dap.config.Config;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * @author Florian
 */
public class AuthHelper {

    /**.
     * LOG
     */
    protected static final Logger LOG = LogManager.getLogger();
    /**.
     * Déclaration de AUTHORITY
     */
    private static final String AUTHORITY = "https://login.microsoftonline.com";
    /**.
     * Déclaration de AUTHORIZEURL
     */
    private static final String AUTHORIZEURL = AUTHORITY + "/common/oauth2/v2.0/authorize";

    /**.
     * Déclaration du tableau scopes
     */
    private static String[] scopes = {
            "openid",
            "offline_access",
            "profile",
            "User.Read",
            "Mail.Read",
            "Calendars.Read",
            "Contacts.Read" };

    /**.
     * Déclaration de appId
     */
    private static String appId = null;
    /**.
     * Déclaration de appPassword
     */
    private static String appPassword = null;
    /**.
     * Déclaration de redirectUrl
     */
    private static String redirectUrl = null;

    /**
     * @return appId
     */
    private static String getAppId() {
        if (appId == null) {
            try {
                loadConfig();
            } catch (Exception e) {
                //TODO brf by Djer |Log4J| Une petite log ?
                return null;
            }
        }
        return appId;
    }

    /**
     * @return appPassword
     */
    private static String getAppPassword() {
        if (appPassword == null) {
            try {
                loadConfig();
            } catch (Exception e) {
              //TODO brf by Djer |Log4J| Une petite log ?
                return null;
            }
        }
        return appPassword;
    }

    /**
     * @return redirectUrl
     */
    private static String getRedirectUrl() {
        if (redirectUrl == null) {
            try {
                loadConfig();
            } catch (Exception e) {
              //TODO brf by Djer |Log4J| Une petite log ?
                return null;
            }
        }
        return redirectUrl;
    }

    /**
     * @return un StringBuilder
     */
    private static String getScopes() {
        StringBuilder sb = new StringBuilder();
        LOG.debug("AuthHelper GetScope : " + scopes);
        for (String scope : scopes) {
            sb.append(scope + " ");
        }
        return sb.toString().trim();
    }

    /**
     * @throws IOException fonction
     */
    @SuppressWarnings("unused")
    private static void loadConfig() throws IOException {
        //TODO brf by Djer |IOC| Ne fait pas de NEW ! Cette conf est géré par Spring, injecte-là !
        Config config = new Config();
        String authConfigFile = "auth.properties";
        InputStreamReader authConfigStream = new InputStreamReader(new FileInputStream(config.getAuthProperties()),
                Charset.forName("UTF-8"));
        LOG.debug("Déclaration du chemin pour auth.properties" + authConfigStream);

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
        } else {
            //TODO brf by Djer |Audit Code| Traite le message plutot que de le masquer. Tu as du "dead code" ici car tu fait un "new" sur "authConfigStream" il ne pourra JAMAIS être null (il peut cependant pointer vers un fichier inexistant)
            //TODO brf by Djer |POO| Message pas tout à fait vrai, le fichier n'est pas nécéssairement cherché "in the classpath"
            throw new FileNotFoundException("Property file '" + authConfigFile + "' not found in the classpath.");
        }
    }

    /**
     * @param state le statut
     * @param nonce .
     * @return urlBuilder
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
     * @param authCode .
     * @param tenantId .
     * @return tokenService
     */
    public static TokenResponse getTokenFromAuthCode(final String authCode, final String tenantId) {
        // Create a logging interceptor to log request and responses
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor).build();

        LOG.debug("AuthHelper - TokenResponse affichage du client : " + client);
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
          //TODO brf by Djer |Log4J| Une petite log ?
            TokenResponse error = new TokenResponse();
            error.setError("IOException");
            error.setErrorDescription(e.getMessage());
            return error;
        }
    }

    /**
     * @param tokens .
     * @param tenantId .
     * @return tokenService
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
            LOG.debug("AuthHelper - TokenResponse Affichage de retrofit : " + retrofit);
            TokenService tokenService = retrofit.create(TokenService.class);

            try {
                return tokenService.getAccessTokenFromRefreshToken(tenantId, getAppId(), getAppPassword(),
                        "refresh_token", tokens.getRefreshToken(), getRedirectUrl()).execute().body();
                //TODO brf by Djer |API Microsoft| Re-sauvegarde les nouveaux token en BDD. Sinon après la première expiration tu devra re faire un appel à chaque fois que l'utilisateur fait une nouvelle requete (ceux en BDD seront forcèment expiré)
            } catch (IOException e) {
              //TODO brf by Djer |Log4J| Une petite log ?
                TokenResponse error = new TokenResponse();
                error.setError("IOException");
                error.setErrorDescription(e.getMessage());
                return error;
            }
        }
    }
}