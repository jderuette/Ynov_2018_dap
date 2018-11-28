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

    private Logger logger = LogManager.getLogger();

    @Autowired
    private Config configuration;

    @Autowired
    private MicrosoftAccountRepository microsoftAccountRepository;

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

    protected static String getScopes() {
        StringBuilder sb = new StringBuilder();
        for (String scope : scopes) {
            sb.append(scope + " ");
        }
        return sb.toString().trim();
    }

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
