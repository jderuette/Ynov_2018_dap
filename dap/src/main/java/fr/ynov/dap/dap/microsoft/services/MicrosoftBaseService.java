package fr.ynov.dap.dap.microsoft.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import fr.ynov.dap.dap.Config;
import fr.ynov.dap.dap.data.MicrosoftAccount;
import fr.ynov.dap.dap.data.MicrosoftAccountRepostory;
import fr.ynov.dap.dap.exception.SecretFileAccesException;
import fr.ynov.dap.dap.microsoft.services.CallService.MicrosoftService;
import fr.ynov.dap.dap.microsoft.services.CallService.TokenService;
import fr.ynov.dap.dap.model.TokenResponse;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 *
 * @author David_tepoche
 *
 */
abstract class MicrosoftBaseService {
    /**
     * the name of the properties in file.
     */
    private static final String REDIRECT_URL = "redirectUrl";

    /**
     * the name of the properties in file.
     */
    private static final String APP_PASSWORD = "appPassword";

    /**
     * the name of the properties in file.
     */
    private static final String APP_ID = "appId";

    /**
     * link the config.
     */
    @Autowired
    private Config config;

    /**
     * link msAccountRepository.
     */
    @Autowired
    private MicrosoftAccountRepostory microsoftAccountRepostory;

    /**
     * stock the instance,it is not usefull to read each time the client'secret
     * file.
     */
    private static Properties clientProperties;

    /**
     * Initialize the logger.
     */
    private Logger logger = LogManager.getLogger(getClassName());

    /**
     * list of all the scope required for the appli.
     */
    private static final List<String> SCOPES = Arrays.asList("openid", "offline_access", "profile", "User.Read",
            "Mail.Read", "Calendars.Read", "Contacts.Read");

    /**
     * get the name of the class.
     *
     * @return the name of the class
     */
    protected abstract String getClassName();

    /**
     * @return the scopes
     */
    protected static String getScopes() {
        StringBuilder sb = new StringBuilder();
        for (String scope : SCOPES) {
            sb.append(scope + " ");
        }
        return sb.toString().trim();
    }

    /**
     * @return the config
     */
    protected Config getConfig() {
        return config;
    }

    /**
     * get properties from microsoft properties client' secret.
     *
     * @return Porperties with all the info from the file with the client'secret
     * @throws SecretFileAccesException throw if you can't get the info from the
     *                                  properties
     */
    private Properties getClientProperties() throws SecretFileAccesException {
        if (clientProperties == null) {
            clientProperties = new Properties();
            try (FileInputStream istreamClientSecretFile = new FileInputStream(
                    config.getDataStoreDirectory() + File.separator + config.getMicrosoftClientSecretFile())) {
                if (istreamClientSecretFile != null) {
                    clientProperties.load(istreamClientSecretFile);
                }
            } catch (IOException e) {
                throw new SecretFileAccesException(e);
            }
        }
        return clientProperties;
    }

    /**
     * refresh the token if the experation date is expired, save directly in the
     * msAccount Bdd.
     *
     * @param microsoftAccount msAccount
     * @throws IOException              throw if fail to get the new token
     * @throws SecretFileAccesException throw if you can't get the info from the
     *                                  properties
     */
    public void ensureTokens(final MicrosoftAccount microsoftAccount) throws IOException, SecretFileAccesException {

        // Are tokens still valid?
        Date expirationDate = microsoftAccount.getExpirationDate();
        String refreshToken = microsoftAccount.getRefreshToken();

        Calendar now = Calendar.getInstance();
        if (now.getTime().after(expirationDate)) {
            // Expired, refresh the tokens
            // Create a logging interceptor to log request and responses
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            // Create and configure the Retrofit object
            Retrofit retrofit = new Retrofit.Builder().baseUrl(getConfig().getMicrosoftAuthorityUrl()).client(client)
                    .addConverterFactory(JacksonConverterFactory.create()).build();

            // Generate the token service
            TokenService tokenService = retrofit.create(TokenService.class);

            TokenResponse tokenResponse = tokenService.getAccessTokenFromRefreshToken(microsoftAccount.getTenantId(),
                    getAppId(), getAppPassword(), "refresh_token", refreshToken, getRedirectUrl()).execute().body();

            microsoftAccount.setAccessToken(tokenResponse.getAccessToken());
            microsoftAccount.setExpirationDate(tokenResponse.getExpirationTime());
            microsoftAccount.setRefreshToken(tokenResponse.getRefreshToken());
            microsoftAccount.setTokenType(tokenResponse.getTokenType());

            microsoftAccountRepostory.save(microsoftAccount);

        }

    }

    /**
     * get the Microsoftservice used for ask the api provided by microsoft.
     *
     * @param microsoftAccount msAccount
     * @return the microsoft service
     * @throws SecretFileAccesException throw if you can't get the info from the
     *                                  properties
     * @throws IOException              if the ensure token fail
     */
    protected MicrosoftService getMicrosoftService(final MicrosoftAccount microsoftAccount)
            throws IOException, SecretFileAccesException {

        ensureTokens(microsoftAccount);

        // Create a logging interceptor to log request and responses
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        Interceptor requestInterceptor = new Interceptor() {
            @Override
            public Response intercept(final Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder().header("User-Agent", "java-tutorial")
                        .header("client-request-id", UUID.randomUUID().toString())
                        .header("return-client-request-id", "true")
                        .header("Authorization", String.format("Bearer %s", microsoftAccount.getAccessToken()))
                        .method(original.method(), original.body()).build();

                return chain.proceed(request);
            }
        };

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(requestInterceptor).addInterceptor(interceptor)
                .build();

        // Create and configure the Retrofit object
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://graph.microsoft.com").client(client)
                .addConverterFactory(JacksonConverterFactory.create()).build();
        return retrofit.create(MicrosoftService.class);

    }

    /**
     * get the redirect url password from properties.
     *
     * @return redirect url from client'secret file
     * @throws SecretFileAccesException throw if you can't get the info from the
     *                                  properties
     */
    protected String getRedirectUrl() throws SecretFileAccesException {
        return getClientProperties().getProperty(REDIRECT_URL);
    }

    /**
     * get the application password from properties.
     *
     * @return password from client'secret file
     * @throws SecretFileAccesException throw if you can't get the info from the
     *                                  properties
     */
    protected String getAppPassword() throws SecretFileAccesException {
        return getClientProperties().getProperty(APP_PASSWORD);
    }

    /**
     * get the application id from properties..
     *
     * @return app id from client'secret file
     * @throws SecretFileAccesException throw if you can't get the info from the
     *                                  properties
     */
    protected String getAppId() throws SecretFileAccesException {
        return getClientProperties().getProperty(APP_ID);
    }

    /**
     * @return the logger
     */
    protected Logger getLogger() {
        return logger;
    }
}
