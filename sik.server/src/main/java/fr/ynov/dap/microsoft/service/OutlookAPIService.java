package fr.ynov.dap.microsoft.service;

import java.io.IOException;
import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import fr.ynov.dap.Config;
import fr.ynov.dap.contract.MicrosoftAccountRepository;
import fr.ynov.dap.exception.NoConfigurationException;
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
//TODO sik by Djer |POO| Tu pourrias créer une classe aprente aux Service Googet et Microsoft avec : la Config, le Logger, et des methdoe utilitaire (hasAccount(userkey) : abstract car différente pour Google et Microsoft, isConfValid(),...)
public class OutlookAPIService {

    /**
     * Logger instance.
     */
    //TODO sik by Djer |Log4J| Devrait être satic final. Créer un Logger est couteux. Attention si les classes "fille" l'utilise la catégorie ne sera pas forcément celle désirée.
    private Logger logger = LogManager.getLogger();

    /**
     * Current configuration.
     */
    @Autowired
    private Config config;

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
    protected static final String AUTHORIZE_URL = AUTHORITY + "/common/oauth2/v2.0/authorize/";

    /**
     * Every scopes needed.
     */
    private static String[] scopes = { MicrosoftScopes.OPEN_ID, MicrosoftScopes.OFFLINE_ACCESS, MicrosoftScopes.PROFILE,
            MicrosoftScopes.USER_READ, MicrosoftScopes.MAIL_READ, MicrosoftScopes.MAIL_READ_WRITE,
            MicrosoftScopes.CALENDARS_READ, MicrosoftScopes.CONTACTS_READ };

    /**
     * Default constructor.
     */
    public OutlookAPIService() {

    }

    /**
     * Get current instance of logger.
     * @return Logger
     */
    //TODO sik by Djer |POO| Protecetd serait suffisant
    public Logger getLogger() {
        return logger;
    }

    /**
     * Get current instance of configuration.
     * @return Configuration
     */
  //TODO sik by Djer |POO| Protecetd serait suffisant
    public Config getConfig() {
        return config;
    }

    /**
     * Return every scopes.
     * @return Scopes
     */
    protected static String getScopes() {
        StringBuilder sb = new StringBuilder();
        String space = " ";
        for (String scope : scopes) {
            sb.append(scope);
            sb.append(space);
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

        if (config == null) {
            throw new NoConfigurationException();
        }

        TokenService tokenService = createTokenService();

        String appId = config.getMicrosoftAppId();
        String appPassword = config.getMicrosoftAppPassword();
        String redirectUrl = config.getMicrosoftRedirectUrl();

        Response<TokenResponse> resp = tokenService
                .getAccessTokenFromAuthCode(tenantId, appId, appPassword, "authorization_code", authCode, redirectUrl)
                .execute();

        return resp.body();

    }

    /**
     * Check if token need to be refresh and refresh it if needed.
     * @param msAcc User Microsoft account
     * @return New token
     * @throws IOException Exception
     */
    public TokenResponse ensureTokens(final MicrosoftAccount msAcc) throws IOException {

        if (config == null) {
            throw new NoConfigurationException();
        }

        if (msAcc == null) {

            getLogger().warn("MicrosoftAccount is null. Null token is return");

            return null;
        }

        TokenResponse tokens = msAcc.getToken();
        String tenantId = msAcc.getTenantId();

        Calendar now = Calendar.getInstance();

        if (now.getTime().before(tokens.getExpirationTime())) {

            getLogger().info("Token is still valid.");

            return tokens;

        } else {

            getLogger().warn("Token is invalid. Try to refresh it.");

            TokenService tokenService = createTokenService();

            String appId = config.getMicrosoftAppId();
            String appPassword = config.getMicrosoftAppPassword();
            String redirectUrl = config.getMicrosoftRedirectUrl();

            TokenResponse newToken = tokenService.getAccessTokenFromRefreshToken(tenantId, appId, appPassword,
                    "refresh_token", tokens.getRefreshToken(), redirectUrl).execute().body();

            return newToken;

        }

    }

    /**
     * Get a token. Refresh it if needed and save it on database.
     * @param msAcc User Microsoft account
     * @return New token from Microsoft Graph API
     * @throws IOException Exception
     */
    public final TokenResponse getToken(final MicrosoftAccount msAcc) throws IOException {
        TokenResponse newTokens = ensureTokens(msAcc);
        if (newTokens != null) {
            msAcc.setToken(newTokens);
            msAccountRepository.save(msAcc);
        }
        return newTokens;
    }

    /**
     * Create token service from interface.
     * @return Instance of token service
     */
    public final TokenService createTokenService() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(AUTHORIZE_URL).client(client)
                .addConverterFactory(JacksonConverterFactory.create()).build();

        return retrofit.create(TokenService.class);

    }

}
