package fr.ynov.dap.microsoft;

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

import fr.ynov.dap.data.microsoft.TokenResponse;

/**
 * Microsoft authentication helper.
 * @author MBILLEMAZ
 *
 */
public final class AuthHelper {
    /**
     * default url.
     */
    private static final String AUTHORITY = "https://login.microsoftonline.com";
    /**
     * Authorization url.
     */
    private static final String AUTHORIZE_URL = AUTHORITY + "/common/oauth2/v2.0/authorize";

    /**
     * Asked rights.
     */
    private static String[] scopes = { "openid", "offline_access", "profile", "User.Read", "Mail.Read", "Calendars.Read", "Contacts.Read"};

    /**
     * appId.
     */
    private static String appId = null;
    /**
     * appPassword.
     */
    private static String appPassword = null;
    /**
     * redirect URL.
     */
    private static String redirectUrl = null;

    /**
     * private constructor.
     */
    private AuthHelper() {

    }

    /**
     * get app id.
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
     * get app password.
     * @return app password.
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
     * get redirect url.
     * @return redirect url
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
     * get scopes.
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
     * load config with auth.properties.
     * @throws IOException if file not found
     */
    private static void loadConfig() throws IOException {
        String authConfigFile = "auth.properties";
        InputStream authConfigStream = AuthHelper.class.getClassLoader().getResourceAsStream(authConfigFile);

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
     * get login url.
     * @param state id ?
     * @param nonce id ?
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
     * get Token response.
     * @param authCode authentication code
     * @param tenantId tenant id
     * @return token response
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
     * if token is expired, refresh it.
     * @param tokens to test
     * @param tenantId tenant
     * @return refreshed token
     */
    public static TokenResponse ensureTokens(TokenResponse tokens, String tenantId) {
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