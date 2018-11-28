package fr.ynov.dap.microsoft;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Properties;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.util.UriComponentsBuilder;

import fr.ynov.dap.data.TokenResponse;
import fr.ynov.dap.microsoft.auth.TokenService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Base service for Microsoft API, called AuthHelperin the Microsoft tutorial.
 * @author Robin DUDEK
 *
 */
public class MicrosoftService {

    /**
     * Log4j instance for all children microsoft services.
     */
    protected static final Logger LOGGER = LogManager.getLogger();

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
    protected static String[] scopes = { "openid", "offline_access", "profile", "User.Read", "Mail.Read",
            "mail.readwrite", "Calendars.Read", "Contacts.Read" };

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
    public MicrosoftService() {

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
     * Construct login url.
     * @param state Current state
     * @param nonce Current nonce
     * @return Login url
     */
    public static String getLoginUrl(UUID state, UUID nonce) {

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
     * Load configuration.
     * @throws IOException Exception
     */
    protected static void loadConfig() throws IOException {
        String authConfigFile = "auth.properties";
        InputStream authConfigStream = MicrosoftService.class.getClassLoader()
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

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(AUTHORITY).client(client)
                .addConverterFactory(JacksonConverterFactory.create()).build();

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
     * Chech if token need to be refresh and refresh it if neede.
     * @param tokens Current token response
     * @param tenantId Tenant Id
     * @return New token
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
                TokenResponse error = new TokenResponse();
                error.setError("IOException");
                error.setErrorDescription(e.getMessage());
                return error;
            }
        }
    }

}
