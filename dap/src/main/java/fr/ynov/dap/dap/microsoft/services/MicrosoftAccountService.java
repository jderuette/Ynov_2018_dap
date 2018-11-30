package fr.ynov.dap.dap.microsoft.services;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepostory;
import fr.ynov.dap.dap.data.MicrosoftAccount;
import fr.ynov.dap.dap.exception.SecretFileAccesException;
import fr.ynov.dap.dap.microsoft.services.CallService.TokenService;
import fr.ynov.dap.dap.model.MicrosoftUserDetail;
import fr.ynov.dap.dap.model.TokenResponse;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 *
 * @author David_tepoche
 *
 */
@Service
public class MicrosoftAccountService extends MicrosoftBaseService {
    /**
     * link the AppUserRepostory.
     */
    @Autowired
    private AppUserRepostory appUserRepostory;

    /**
     * retrieve token from authCode.
     *
     * @param authCode code required for the auth
     * @param tenantId code from the msAccountSaved
     * @return the tokenResponse
     * @throws SecretFileAccesException throw if you can't get the info from the
     *                                  properties
     */
    public TokenResponse getTokenFromAuthCode(final String authCode, final String tenantId)
            throws SecretFileAccesException {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        // Create and configure the Retrofit object
        Retrofit retrofit = new Retrofit.Builder().baseUrl(getConfig().getMicrosoftAuthorityUrl()).client(client)
                .addConverterFactory(JacksonConverterFactory.create()).build();

        // Generate the token service
        TokenService tokenService = retrofit.create(TokenService.class);
        try {
            return tokenService.getAccessTokenFromAuthCode(tenantId, getAppId(), getAppPassword(), "authorization_code",
                    authCode, getRedirectUrl()).execute().body();
        } catch (IOException e) {
            //TODO duv by Djer |Log4J| Contexte ? Au moins le tenant ID (et le nombre de caractères dans le "authCode", ou juste quelques caractères)
            getLogger().error("Probleme lors de la recuperation du token", e);
            TokenResponse error = new TokenResponse();
            return error;
        }
    }

    /**
     * get the url for login.
     *
     * @param state uuid to ensure the security, the uuid should stay the same from
     *              the call to the response
     * @param nonce uuid to ensure the security, the uuid should stay the same from
     *              the call to the response
     * @return ulr with all the param needed
     * @throws SecretFileAccesException throw if you can't get the info from the
     *                                  properties
     */
    public String getLoginUrl(final UUID state, final UUID nonce) throws SecretFileAccesException {

        String url = getConfig().getMicrosoftAuthorityUrl() + getConfig().getMicrosoftRootUrlCallBack();
        UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(url);
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
     * get the user email.
     *
     * @param microsoftAccount the microsoft account.
     * @return the email of the current user
     * @throws IOException              throw if an input or output exception occurs
     * @throws SecretFileAccesException throw if you can't get the info from the
     *                                  properties
     */
    public String getUserEmail(final MicrosoftAccount microsoftAccount) throws IOException, SecretFileAccesException {
        MicrosoftUserDetail detail = getMicrosoftService(microsoftAccount).getUserDetails().execute().body();
        return getEmail(detail);
    }

    /**
     * get the email from the MicrosoftUSerDetail from the ms api.
     *
     * @param detail ms api response
     * @return the email
     */
    private String getEmail(final MicrosoftUserDetail detail) {
        String errorMessage = "Impossible de recuperer les informations personnelles de l'utilisateur";
        if (detail == null) {

            getLogger().error("detail is null when trying to get userdetail");
            throw new NullPointerException(errorMessage);
        }

        String email;
        if (detail.getDisplayName() != null && detail.getDisplayName().contains("@")) {
            email = detail.getDisplayName();
        } else if (detail.getMail() != null) {
            email = detail.getMail();
        } else {
            throw new NullPointerException(errorMessage);
        }
        return email;
    }

    @Override
    public final String getClassName() {
        return MicrosoftBaseService.class.getName();
    }

    /**
     * link the microsoft account with the userKey in bdd.
     *
     * @param accountName accountName of the msAccount
     * @param userKey     user in bdd
     * @param request     http call
     * @param session     http session
     * @param response    http response
     * @param state       uuid to ensure the security, the uuid should stay the same
     *                    from the call to the response
     * @param nonce       uuid to ensure the security, the uuid should stay the same
     *                    from the call to the response
     * @throws IOException              throw if an input or output exception occurs
     * @throws SecretFileAccesException throw if you can't get the info from the
     *                                  properties
     */
    public void addAccount(final String accountName, final String userKey, final HttpServletRequest request,
            final HttpSession session, final HttpServletResponse response, final UUID state, final UUID nonce)
            throws IOException, SecretFileAccesException {
        AppUser appUser = appUserRepostory.findByUserKey(userKey);

        if (appUser == null) {
            getLogger().warn("Ajout d'un compte pour un utilisateur non present en bdd: " + userKey);
            throw new NullPointerException("Utilisateur non present en base de donnée");
        }
        MicrosoftAccount microsoftAccount = appUser.getmAccounts().stream()
                .filter(ma -> ma.getAccountName().equalsIgnoreCase(accountName)).findFirst().orElse(null);
        if (microsoftAccount != null) {
            getLogger().warn("Ajout d'un utilistaeur deja present en Bdd: " + microsoftAccount.getAccountName());
        } else {
            //TODO duv by Djer |MVC| Tien ? Le même code pour "google". Devrait etre dans le controller. De plus gérer la HTTP session c'est bien du travail de controller !
            session.setAttribute("accountName", accountName);
            session.setAttribute("userKey", userKey);

            String loginUrl = null;
            loginUrl = getLoginUrl(state, nonce);
            //TODO duv by Djer |MVC| Renvoyer l'URL de redirection, et laisser le controller gérer l'intéraction avec l'utilisateur
            response.sendRedirect(loginUrl);
        }
    }

    /**
     * save the new accountName in userkey.
     *
     * @param tokenResponse the token response
     * @param userKey       user in bdd
     * @param accountName   nale of the MicrosoftAccountaccount
     * @param tenantId      key of the user
     */
    public void saveNewAccountNameInUserKey(final TokenResponse tokenResponse, final String userKey,
            final String accountName, final String tenantId) {
        AppUser appUser = appUserRepostory.findByUserKey(userKey);
        if (appUser != null) {
            MicrosoftAccount account = new MicrosoftAccount();
            account.setAccountName(accountName);
            account.setAccessToken(tokenResponse.getAccessToken());
            account.setRefreshToken(tokenResponse.getRefreshToken());
            account.setExpirationDate(tokenResponse.getExpiresIn());
            account.setTenantId(tenantId);
            appUser.addMicrosoftAccount(account);

            appUserRepostory.save(appUser);
        } else {
            throw new NullPointerException("The userKey isn't exist in DB : " + userKey);
        }
    }
}
