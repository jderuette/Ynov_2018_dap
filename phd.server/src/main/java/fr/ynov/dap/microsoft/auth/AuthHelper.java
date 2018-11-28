package fr.ynov.dap.microsoft.auth;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Properties;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.util.UriComponentsBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 *
 * @author Dom
 *
 */
public final class AuthHelper {
    /**
    *
    */
    private AuthHelper() {

    }

    /**
     *
     */
    private static final Logger LOG = LogManager.getLogger();

    /**.
     * AUTH_PROPERTIES_FILE_PATH  is a variable containing the file path of the auth.properties
     */
    private static final String AUTH_PROPERTIES_FILE_PATH = System.getProperty("user.home")
            + System.getProperty("file.separator") + "dap" + System.getProperty("file.separator") + "auth.properties";

    /**
     *
     */
    private static final String AUTHORITY = "https://login.microsoftonline.com";
    /**
     *
     */
    private static final String AUTHORIZE_URL = AUTHORITY + "/common/oauth2/v2.0/authorize";
    /**
     *
     */
    private static String[] scopes = {"openid", "offline_access", "profile", "User.Read", "Mail.Read", "Calendars.Read",
            "Contacts.Read"};

    /**
     *
     */
    private static String appId = null;
    /**
     *
     */
    private static String appPassword = null;
    /**
     *
     */
    private static String redirectUrl = null;

    /**
     *
     * @return .
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
     *
     * @return .
     */
    @SuppressWarnings("unused")
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
     *
     * @return .
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
     *
     * @return .
     */

    private static String getScopes() {
        StringBuilder sb = new StringBuilder();
        for (String scope : scopes) {
            sb.append(scope + " ");
        }
        return sb.toString().trim();
    }

    /**
     *
     * @throws IOException .
     */
    private static void loadConfig() throws IOException {
        InputStreamReader authConfigStreamReader = new InputStreamReader(new FileInputStream(AUTH_PROPERTIES_FILE_PATH),
                Charset.forName("UTF-8"));

        if (authConfigStreamReader != null && authConfigStreamReader.ready()) {
            Properties authProps = new Properties();
            LOG.info("past the auth.properties in : " + AUTH_PROPERTIES_FILE_PATH);
            try {
                authProps.load(authConfigStreamReader);
                appId = authProps.getProperty("appId");
                appPassword = authProps.getProperty("appPassword");
                redirectUrl = authProps.getProperty("redirectUrl");
            } finally {
                authConfigStreamReader.close();
            }
        } else {
            InputStream authConfigStream = AuthHelper.class.getClassLoader()
                    .getResourceAsStream(AUTH_PROPERTIES_FILE_PATH);
            LOG.info("past the auth.properties in : " + AUTH_PROPERTIES_FILE_PATH);
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
    }

    /**
     *
     * @param state .
     * @param nonce .
     * @return .
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
     *
     * @param authCode .
     * @param tenantId .
     * @return .
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
     * .
     * @param tokens .
     * @param tenantId .
     * @return .
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

            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            // Create and configure the Retrofit object
            Retrofit retrofit = new Retrofit.Builder().baseUrl(AUTHORITY).client(client)
                    .addConverterFactory(JacksonConverterFactory.create()).build();

            // Generate the token service
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
