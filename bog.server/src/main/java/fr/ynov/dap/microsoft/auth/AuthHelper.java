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
 * @author Mon_PC
 */
public final class AuthHelper {

    /**.
     * Log variable
     */
    private static final Logger LOG = LogManager.getLogger();
    /**.
     * propriété authority
     */
    private static final String AUTHORITY = "https://login.microsoftonline.com";
    /**.
     * propriété authorizeUrl
     */
    private static final String AUTHORIZE_URL = AUTHORITY + "/common/oauth2/v2.0/authorize";
    /**.
     * propriété scopes
     */
    private static String[] scopes = {"openid", "offline_access", "profile", "User.Read", "Mail.Read", "Calendars.Read",
            "Contacts.Read"};
    /**.
     * propriété appId
     */
    private static String appId = null;
    /**.
     * propriété appPassword
     */
    private static String appPassword = null;
    /**.
     * propriété redirectUrl
     */
    private static String redirectUrl = null;

    /**.
     * Répertoire de base du authConfigFile
     */
    private static final String AUTH_CONFIG_FILE = System.getProperty("user.home")
            + System.getProperty("file.separator") + "dap" + System.getProperty("file.separator") + "microsoft"
            + System.getProperty("file.separator") + "auth.properties";

    /**.
     * Propriété authConfigFile
     */
    private static String authConfigFile;

    /**.
     * Constructeur classe
     */
    private AuthHelper() {
    }

    /**
     * @return authConfigFile
     */
    public static String getAuthConfigFile() {
        return authConfigFile;
    }

    /**
     * @param newAuthConfigFile nouveau authConfigFile
     */
    public static void setAuthConfigFile(final String newAuthConfigFile) {
        authConfigFile = newAuthConfigFile;
    }

    /** .
     * @return appId
     */
    private static String getAppId() {
        if (appId == null) {
            try {
                authConfigFile = AUTH_CONFIG_FILE;
                loadConfig();
            } catch (Exception e) {
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
                return null;
            }
        }
        return redirectUrl;
    }

    /**
     * @return scopes
     */
    private static String getScopes() {
        StringBuilder sb = new StringBuilder();
        for (String scope : scopes) {
            sb.append(scope + " ");
        }
        return sb.toString().trim();
    }

    /**
     * @throws IOException property file not found
     */
    private static void loadConfig() throws IOException {
        InputStreamReader authConfigStreamReader = new InputStreamReader(new FileInputStream(authConfigFile),
                Charset.forName("UTF-8"));

        if (authConfigStreamReader != null && authConfigStreamReader.ready()) {
            Properties authProps = new Properties();
            LOG.info("FILE_PATH : " + authConfigFile);
            try {
                authProps.load(authConfigStreamReader);
                appId = authProps.getProperty("appId");
                appPassword = authProps.getProperty("appPassword");
                redirectUrl = authProps.getProperty("redirectUrl");
            } finally {
                authConfigStreamReader.close();
            }
        } else {
            InputStream authConfigStream = AuthHelper.class.getClassLoader().getResourceAsStream(authConfigFile);
            LOG.info("FILE_PATH : " + authConfigFile);
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
     * @param state of the UUID
     * @param nonce of the nonce
     * @return loginUrl
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
     * @param authCode .
     * @param tenantId .
     * @return token
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
     * @param tokens response
     * @param tenantId identifiant tenant
     * @return ensure tokens
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
