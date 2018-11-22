package fr.ynov.dap.microsoft.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;

import fr.ynov.dap.contract.MicrosoftAccountRepository;
import fr.ynov.dap.microsoft.MicrosoftScopes;
import fr.ynov.dap.microsoft.contract.TokenService;
import fr.ynov.dap.microsoft.model.TokenResponse;
import fr.ynov.dap.model.microsoft.MicrosoftAccount;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Base service for Microsoft API management.
 * @author Kévin Sibué
 *
 */
public class OutlookAPIService {

    /**
     * Current configuration.
     */
    @Autowired
    private MicrosoftAccountRepository msAccountRepository;

    /**
     * Constant than store authority value.
     */
    protected static final String AUTHORITY = "https://login.microsoftonline.com";

    /**
     * Constant that store authorize url.
     */
    protected static final String AUTHORIZE_URL = AUTHORITY + "/common/oauth2/v2.0/authorize";

    /**
     * Every scopes needed.
     */
    protected static String[] scopes = { MicrosoftScopes.OPEN_ID, MicrosoftScopes.OFFLINE_ACCESS,
            MicrosoftScopes.PROFILE, MicrosoftScopes.USER_READ, MicrosoftScopes.MAIL_READ,
            MicrosoftScopes.MAIL_READ_WRITE, MicrosoftScopes.CALENDARS_READ, MicrosoftScopes.CONTACTS_READ };

    /**
     * store app id.
     */
    private static String appId = null;

    /**
     * Store app password.
     */
    private static String appPassword = null;

    /**
     * Store redirect url.
     */
    private static String redirectUrl = null;

    /**
     * Default constructor.
     */
    public OutlookAPIService() {

    }

    /**
     * Get app id or load config.
     * @return App id
     */
    protected static String getAppId() {
        if (appId == null) {
            try {
                loadConfig();
            } catch (Exception e) {
                return null;
            }
        }
        return appId;
    }

    /**
     * Get app password or load config.
     * @return App password
     */
    protected static String getAppPassword() {
        if (appPassword == null) {
            try {
                loadConfig();
            } catch (Exception e) {
                return null;
            }
        }
        return appPassword;
    }

    /**
     * Get redirect url or load config.
     * @return Redirect url
     */
    protected static String getRedirectUrl() {
        if (redirectUrl == null) {
            try {
                loadConfig();
            } catch (Exception e) {
                return null;
            }
        }
        return redirectUrl;
    }

    /**
     * Return every scopes.
     * @return Scopes
     */
    protected static String getScopes() {
        StringBuilder sb = new StringBuilder();
        for (String scope : scopes) {
            sb.append(scope + " ");
        }
        return sb.toString().trim();
    }

    /**
     * Load configuration.
     * @throws IOException Exception
     */
    protected static void loadConfig() throws IOException {
        String authConfigFile = "auth.properties";
        InputStream authConfigStream = MicrosoftAccountService.class.getClassLoader()
                .getResourceAsStream(authConfigFile);

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
     * Retrieve token from auth code.
     * @param authCode AuthCode
     * @param tenantId TenantId
     * @return Token response
     */
    public static TokenResponse getTokenFromAuthCode(final String authCode, final String tenantId) {
        // Create a logging interceptor to log request and responses
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        // Create and configure the Retrofit object
        Retrofit retrofit = new Retrofit.Builder().baseUrl(AUTHORITY).client(client)
                .addConverterFactory(JacksonConverterFactory.create()).build();

        // Generate the token service
        TokenService tokenService = retrofit.create(TokenService.class);

        try {
            Response<TokenResponse> resp = tokenService.getAccessTokenFromAuthCode(tenantId, getAppId(),
                    getAppPassword(), "authorization_code", authCode, getRedirectUrl()).execute();
            return resp.body();
        } catch (IOException e) {
            TokenResponse error = new TokenResponse();
            error.setError("IOException");
            error.setErrorDescription(e.getMessage());
            return error;
        }
    }

    /**
     * Check if token need to be refresh and refresh it if needed.
     * @param msAcc User Microsoft account
     * @return New token
     */
    public static TokenResponse ensureTokens(final MicrosoftAccount msAcc) {

        if (msAcc == null) {
            return null;
        }

        TokenResponse tokens = msAcc.getToken();
        String tenantId = msAcc.getTenantId();

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

                TokenResponse newToken = tokenService.getAccessTokenFromRefreshToken(tenantId, getAppId(),
                        getAppPassword(), "refresh_token", tokens.getRefreshToken(), getRedirectUrl()).execute().body();

                return newToken;

            } catch (IOException e) {
                TokenResponse error = new TokenResponse();
                error.setError("IOException");
                error.setErrorDescription(e.getMessage());
                return error;
            }

        }

    }

    /**
     * Get a token. Refresh it if needed and save it on database.
     * @param msAcc User Microsoft account
     * @return New token from Microsoft Graph API
     */
    public final TokenResponse getToken(final MicrosoftAccount msAcc) {
        TokenResponse newTokens = OutlookAPIService.ensureTokens(msAcc);
        if (newTokens != null) {
            msAcc.setToken(newTokens);
            msAccountRepository.save(msAcc);
        }
        return newTokens;
    }

}
