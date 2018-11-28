package fr.ynov.dap.microsoft.authentication;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Properties;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import fr.ynov.dap.Config;
import fr.ynov.dap.data.microsoft.MicrosoftAccount;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Class to help user to login on Microsoft OAuth2.
 * @author thibault
 *
 */
@Component
public final class AuthHelper {
    /**
     * Base URL of microsoft login.
     */
    private static final String AUTHORITY = "https://login.microsoftonline.com";

    /**
     * Authorize PATH of microsoft oauth.
     */
    private static final String AUTHORIZE_URL = AUTHORITY + "/common/oauth2/v2.0/authorize";

    /**
     * Scopes of microsoft api.
     */
    private static final String[] SCOPES = {"openid", "offline_access", "profile", "User.Read", "Mail.Read",
            "Calendars.Read", "Contacts.Read" };

    /**
     * Application ID.
     */
    private static String appId = null;

    /**
     * Application password.
     */
    private static String appPassword = null;

    /**
     * Redirect URL.
     */
    private static String redirectUrl = null;

    /**
     * Redirect URL.
     */
    private static Config config;

    /**
     * Private constructor, because is utils class.
     * @param configToSet Configuration of app
     */
    @Autowired
    private AuthHelper(final Config configToSet) {
        AuthHelper.config = configToSet;
    }

    /**
     * Get microsoft application id.
     * @return app id.
     */
    private static String getAppId() {
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
     * Get microsoft application password.
     * @return password
     */
    private static String getAppPassword() {
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
     * Get redirect URL of microsoft.
     * @return url
     */
    private static String getRedirectUrl() {
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
     * Get scopes of Microsoft.
     * @return scopes.
     */
    private static String getScopes() {
        StringBuilder sb = new StringBuilder();
        for (String scope : SCOPES) {
            sb.append(scope).append(' ');
        }
        return sb.toString().trim();
    }

    /**
     * Load config of Microsoft Application from config file.
     * @throws IOException if file auth.properties not found.
     */
    private static void loadConfig() throws IOException {
        String authConfigFile = "auth.properties";
        InputStream authConfigStream = new FileInputStream(config.getAuthPropertiesPath());

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
     * Get login URL of Microsoft Oauth2.
     * @param state random state
     * @param nonce random nonce
     * @return login url
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
     * Generate token with Auth code OAuth2 microsoft.
     * @param authCode code oauth2
     * @param tenantId tenant ID of microsoft
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
            return tokenService.getAccessTokenFromAuthCode(tenantId, getAppId(), getAppPassword(), "authorization_code",
                    authCode, getRedirectUrl()).execute().body();
        } catch (IOException e) {
            TokenResponse error = new TokenResponse();
            error.setError("IOException");
            error.setErrorDescription(e.getMessage());
            return error;
        }
    }

    /**
     * Ensure validity of token (refresh token if necessary).
     * @param tokens the tokenResponse to check
     * @return MicrosoftAccount A valid microsoft account for API microsoft
     * @throws IOException if error HTTP with microsoft
     */
    public static MicrosoftAccount ensureTokens(final MicrosoftAccount tokens) throws IOException {
        // Are tokens still valid?
        Calendar now = Calendar.getInstance();
        if (now.getTime().getTime() >= tokens.getExpirationTimeMilliseconds()) {
            // Expired, refresh the tokens
            // Create a logging interceptor to log request and responses
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            // Create and configure the Retrofit object
        Retrofit retrofit = new Retrofit.Builder().baseUrl(AUTHORITY).client(client)
                    .addConverterFactory(JacksonConverterFactory.create()).build();

            // Generate the token service
            TokenService tokenService = retrofit.create(TokenService.class);

            TokenResponse response = tokenService.getAccessTokenFromRefreshToken(tokens.getTenantId(), getAppId(),
                    getAppPassword(), "refresh_token", tokens.getRefreshToken(), getRedirectUrl()).execute().body();

            tokens.apply(response);
        }

        return tokens;
    }
}
