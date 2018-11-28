package fr.ynov.dap.microsoft;

import java.io.IOException;
import java.util.Calendar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import fr.ynov.dap.Config;
import fr.ynov.dap.data.microsoft.MicrosoftAccount;
import fr.ynov.dap.data.microsoft.TokenResponse;
import fr.ynov.dap.repository.MicrosoftAccountRepository;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class OutlookAPIService {
	 /**
     * Logger instance.
     */
    private Logger logger = LogManager.getLogger();

    /**
     * Current configuration.
     */
    @Autowired
    private Config configuration;

    /**
     * Current configuration.
     */
    @Autowired
    private MicrosoftAccountRepository microsoftAccountRepository;

    /**
     * Every scopes needed.
     */
    protected static String[] scopes = { 
    		"openid",
    		"offline_access",
    		"profile", 
    		"User.Read", 
    		"Mail.Read",
    		"mail.readwrite", 
    		"Calendars.Read", 
    		"Contacts.Read" 
    };

    /**
     * Default constructor.
     */
    public OutlookAPIService() {

    }

    public Logger getLogger() {
        return logger;
    }

    public Config getConfig() {
        return configuration;
    }

    public String getAuthorizeUrl() {
        return configuration.getMicrosoftAuthorityUrl() + "/common/oauth2/v2.0/authorize";
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
     * Retrieve token from auth code.
     * @param authCode AuthCode
     * @param tenantId TenantId
     * @return Token response
     * @throws IOException Exception
     */
    public TokenResponse getTokenFromAuthCode(final String authCode, final String tenantId) throws IOException {

        TokenService tokenService = createTokenService();

        String appId = configuration.getMicrosoftAppId();
        String appPassword = configuration.getMicrosoftAppPassword();
        String redirectUrl = configuration.getMicrosoftRedirectUrl();

        Response<TokenResponse> resp = tokenService
                .getAccessTokenFromAuthCode(tenantId, appId, appPassword, "authorization_code", authCode, redirectUrl)
                .execute();

        return resp.body();

    }

    /**
     * Check if token need to be refresh and refresh it if needed.
     * @param microsoftAcc User Microsoft account
     * @return New token
     * @throws IOException Exception
     */
    public TokenResponse ensureTokens(final MicrosoftAccount microsoftAcc) throws IOException {

        if (microsoftAcc == null) {

            getLogger().warn("MicrosoftAccount is null. Null token is return");

            return null;
        }

        TokenResponse tokens = microsoftAcc.getToken();
        String tenantId = microsoftAcc.getTenantId();

        Calendar now = Calendar.getInstance();

        if (now.getTime().before(tokens.getExpirationTime())) {

            getLogger().info("Token is still valid.");

            return tokens;

        } else {

            getLogger().warn("Token is invalid. Try to refresh it.");

            TokenService tokenService = createTokenService();

            String appId = configuration.getMicrosoftAppId();
            String appPassword = configuration.getMicrosoftAppPassword();
            String redirectUrl = configuration.getMicrosoftRedirectUrl();

            TokenResponse newToken = tokenService.getAccessTokenFromRefreshToken(tenantId, appId, appPassword,
                    "refresh_token", tokens.getRefreshToken(), redirectUrl).execute().body();

            return newToken;

        }

    }

    /**
     * Get a token. Refresh it if needed and save it on database.
     * @param microsoftAcc User Microsoft account
     * @return New token from Microsoft Graph API
     * @throws IOException Exception
     */
    public final TokenResponse getToken(final MicrosoftAccount microsoftAccount) throws IOException {
        TokenResponse newTokens = ensureTokens(microsoftAccount);
        if (newTokens != null) {
        	microsoftAccount.setToken(newTokens);
            microsoftAccountRepository.save(microsoftAccount);
        }
        return newTokens;
    }

    public final TokenService createTokenService() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(getAuthorizeUrl()).client(client)
                .addConverterFactory(JacksonConverterFactory.create()).build();

        return retrofit.create(TokenService.class);

    }
    
}
