package fr.ynov.dap.helpers;

import fr.ynov.dap.models.microsoft.MicrosoftAccount;
import fr.ynov.dap.models.microsoft.MicrosoftTokenResponse;
import fr.ynov.dap.services.microsoft.TokenService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.web.util.UriComponentsBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.*;
import java.util.*;

/**
 * MicrosoftAuthHelper
 */
public class MicrosoftAuthHelper {

    private static final String authority    = "https://login.microsoftonline.com";
    private static final String authorizeUrl = authority + "/common/oauth2/v2.0/authorize";

    /**
     * Scopes
     */
    private static String[] scopes = {
            "openid",
            "offline_access",
            "profile",
            "User.Read",
            "Mail.Read",
            "Contacts.Read"
    };

    private static String appId       = null;
    private static String appPassword = null;
    private static String redirectUrl = null;

    /**
     * Get Application Id from properties
     *
     * @return AppId
     */
    private static String getAppId() {
        if (appId == null) {
            try {
                loadConfig();
            } catch (Exception e) {
                //TODO grj by Djer |Log4J| Une petite Log ? 
                return null;
            }
        }
        return appId;
    }

    /**
     * Get Application password from properties
     *
     * @return AppPassword
     */
    private static String getAppPassword() {
        if (appPassword == null) {
            try {
                loadConfig();
            } catch (Exception e) {
              //TODO grj by Djer |Log4J| Une petite Log ? 
                return null;
            }
        }
        return appPassword;
    }

    /**
     * Get redirect url from properties
     *
     * @return redirect url
     */
    private static String getRedirectUrl() {
        if (redirectUrl == null) {
            try {
                loadConfig();
            } catch (Exception e) {
              //TODO grj by Djer |Log4J| Une petite Log ? 
                return null;
            }
        }
        return redirectUrl;
    }

    /**
     * Get scopes
     *
     * @return scopes
     */
    private static String getScopes() {
        StringBuilder sb = new StringBuilder();
        for (String scope : scopes) {
            sb.append(scope).append(" ");
        }
        return sb.toString().trim();
    }


    /**
     * Load configuration from properties
     *
     * @throws IOException Exception
     */
    private static void loadConfig() throws IOException {
        String      authConfigFile   = "auth.properties";
        //TODO grj by Djer |Design Patern| Externaliser la configuration ?
        InputStream authConfigStream = MicrosoftAuthHelper.class.getClassLoader().getResourceAsStream(authConfigFile);

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
            throw new FileNotFoundException("Property file '" + authConfigFile + "' not found in the classpath.");
        }
    }

    /**
     * Get login url
     *
     * @param state UUID
     * @param nonce UUID
     * @return login url
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
     * Get token from auth code
     *
     * @param authCode authCode
     * @param tenantId tenantID
     * @return MicrosoftTokenResponse
     */
    public static MicrosoftTokenResponse getTokenFromAuthCode(String authCode, String tenantId) {
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
            MicrosoftTokenResponse error = new MicrosoftTokenResponse();
            error.setError("IOException");
            error.setErrorDescription(e.getMessage());
            return error;
        }
    }

    /**
     * Ensure a token
     *
     * @param microsoftAccount microsoftAccount
     * @return MicrosoftAccount
     */
    //TODO grj by Djer |POO| pas top de "modifier" un paramètre qu'on te passe. En plus ici tu le renvoie si qui est encore plus confusant. Comme le nom de ta méthdoe semble indiquer une modification, ne renvoie rien. Il est aussis "sous-entendu" que l'appelant DOIT sauveagrder le compte, éventuellement, modifié
    public static MicrosoftAccount ensureTokens(MicrosoftAccount microsoftAccount) {
        // Are tokens still valid?
        Calendar now = Calendar.getInstance();
        if (now.getTime().before(microsoftAccount.getTokenExpirationTime())) {
            // Still valid, return them as-is
            return microsoftAccount;
        } else {
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
                MicrosoftTokenResponse microsoftTokenResponse = tokenService.getAccessTokenFromRefreshToken(microsoftAccount.getTenantId(), getAppId(), getAppPassword(),
                        "refresh_token", microsoftAccount.getRefreshToken(), getRedirectUrl()).execute().body();

                microsoftAccount.setToken(microsoftTokenResponse.getAccessToken());
                microsoftAccount.setRefreshToken(microsoftTokenResponse.getRefreshToken());
                microsoftAccount.setTokenExpirationTime(microsoftTokenResponse.getExpirationTime());
            } catch (IOException e) {
              //TODO grj by Djer |Log4J| Une petite Log ? 
                MicrosoftTokenResponse error = new MicrosoftTokenResponse();
                error.setError("IOException");
                error.setErrorDescription(e.getMessage());
            }

            return microsoftAccount;
        }
    }
}
