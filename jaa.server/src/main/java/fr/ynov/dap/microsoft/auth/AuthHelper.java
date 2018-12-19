package fr.ynov.dap.microsoft.auth;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Properties;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import fr.ynov.dap.Config;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Auth Helper used for the Microsoft authentication.
 */
@Component
//TODO jaa by Djer |Spring| "Component" est très générique. Ici tu as un "helper", un "@Service" serait plsu précis (et fera senssiblement la même chose que le @Component)
//TODO jaa by Djer |POO| Un "composant" Spring avec quasiment que du static ca n'est pas très utile. Maintenant que c'est un composnt Spring, ca sera un Singleton, tu peux donc faire du "vrai objet" et utiliser du "non" static" sans risque de "performance". Avec des vrais attributs tu gagneras en plus la possibilté de les modifier facilement, et si besoin plus tard, d'en avoir plusieurs versions (en désactivant le singleton)
public final class AuthHelper {
    /**
     * Config.
     */
    private static Config config;
    /**
     * Config instantiate thanks to the dependency injection.
     * @param conf config file.
     */
    //TODO jaa by Djer |Spring| Pour rester cohérant avec le reste de ton code, met le @Autowired sur l'atribut (injection apr attributs). Ici tu fait, exceptionnellement, de l'injection par constructeur
    @Autowired
    private AuthHelper(final Config conf) {
        config = conf;
    }

    /**
     * Authority.
     */
    //TODO jaa by Djer |POO| Place tes constantes au début de la classe
    private static final String AUTHORITY = "https://login.microsoftonline.com";

    /**
     * Authority Url.
     */
    private static final String AUTHORIZE_URL = AUTHORITY + "/common/oauth2/v2.0/authorize";

    /**
     * Scopes used for authorization.
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
     * Application Id.
     */
    private static String appId = null;
    /**
     * Application password.
     */
    private static String appPassword = null;
    /**
     * Redirect Url.
     */
    private static String redirectUrl = null;

    /**
     * private constructor.
     */
    private AuthHelper() {
    }

    /**
     * Get application id from the auth.properties file.
     * @return application id.
     */
    private static String getAppId() {
        if (appId == null) {
            try {
                loadConfig();
            } catch (Exception e) {
                //TODO jaa by Djer |Log4J| Une petite log ?
                return null;
            }
        }
        return appId;
    }

    /**
     * Get application password from the auth.properties file.
     * @return application password
     */
    private static String getAppPassword() {
        if (appPassword == null) {
            try {
                loadConfig();
            } catch (Exception e) {
              //TODO jaa by Djer |Log4J| Une petite log ?
                return null;
            }
        }
        return appPassword;
    }

    /**
     * Get redirect url from the auth.properties file.
     * @return redirection url used for authentication.
     */
    private static String getRedirectUrl() {
        if (redirectUrl == null) {
            try {
                loadConfig();
            } catch (Exception e) {
              //TODO jaa by Djer |Log4J| Une petite log ?
                return null;
            }
        }
        return redirectUrl;
    }

    /**
     * Concatenate scopes in a string.
     * @return scopes in a string.
     */
    private static String getScopes() {
        StringBuilder sb = new StringBuilder();
        for (String scope : scopes) {
            sb.append(scope + " ");
        }
        return sb.toString().trim();
    }

    /**
     * Load auth.properties configuration file.
     * @throws IOException exception.
     */
    private static void loadConfig() throws IOException {
        String authPropertiesFilePath = config.getMicrosoftAuthPropertiesDefaultPath();
        InputStreamReader authConfigStream = new InputStreamReader(
                new FileInputStream(authPropertiesFilePath), Charset.forName("UTF-8"));

        if (authConfigStream != null) {
            Properties authProps = new Properties();
            try {
                authProps.load(authConfigStream);
                //TODO jaa by Djer |Design Patern| Attention, tu "perd" le "zero" du ZeroConf. Met des valeurs par defaut, et ne remplace ton attribut QUE s'il y a une valeur (non null) dans la config
                appId = authProps.getProperty("appId");
                appPassword = authProps.getProperty("appPassword");
                redirectUrl = authProps.getProperty("redirectUrl");
            } finally {
                authConfigStream.close();
            }
        } else {
            //TODO jaa by Djer |IDE| Ton IDE te dit que c'est du code mort. En effet tu fait une "new" sur authConfigStream, il ne pourra donc JAMAIS être null (il pourait cependant pointer vers un fichier "inexistant")
            throw new FileNotFoundException("Property file '"
                + authPropertiesFilePath + "' not found in the classpath.");
        }
    }

    /**
     * Login Url builder.
     * @param state the state.
     * @param nonce the nonce.
     * @return the login url.
     */
    public static String getLoginUrl(final UUID state, final UUID nonce) {

        UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(AUTHORIZE_URL);
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
     * Get the token from the auth code.
     * @param authCode authentication code.
     * @param tenantId tenant id.
     * @return the token.
     */
    public static TokenResponse getTokenFromAuthCode(final String authCode, final String tenantId) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(AUTHORITY).client(client)
                .addConverterFactory(JacksonConverterFactory.create()).build();

        TokenService tokenService = retrofit.create(TokenService.class);

        try {
            return tokenService.getAccessTokenFromAuthCode(tenantId, getAppId(), getAppPassword(), "authorization_code",
                    authCode, getRedirectUrl()).execute().body();
        } catch (IOException e) {
          //TODO jaa by Djer |Log4J| Une petite log ?
            TokenResponse error = new TokenResponse();
            error.setError("IOException");
            error.setErrorDescription(e.getMessage());
            return error;
        }
    }

    /**
     * Refresh the token if it has expired.
     * @param tokens the token.
     * @param tenantId the tenant id.
     * @return valid token
     */
    public static TokenResponse ensureTokens(final TokenResponse tokens, final String tenantId) {
        Calendar now = Calendar.getInstance();
        if (now.getTime().before(tokens.getExpirationTime())) {
            return tokens;
        } else {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            Retrofit retrofit = new Retrofit.Builder().baseUrl(AUTHORITY).client(client)
                    .addConverterFactory(JacksonConverterFactory.create()).build();

            TokenService tokenService = retrofit.create(TokenService.class);

            try {
                return tokenService.getAccessTokenFromRefreshToken(tenantId, getAppId(), getAppPassword(),
                        "refresh_token", tokens.getRefreshToken(), getRedirectUrl()).execute().body();
            } catch (IOException e) {
              //TODO jaa by Djer |Log4J| Une petite log ?
                TokenResponse error = new TokenResponse();
                error.setError("IOException");
                error.setErrorDescription(e.getMessage());
                return error;
            }
        }
    }
}
