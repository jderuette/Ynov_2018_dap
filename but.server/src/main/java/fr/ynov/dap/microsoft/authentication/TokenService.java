package fr.ynov.dap.microsoft.authentication;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Interface of route to Microsoft OAuth2 login.
 * @author thibault
 *
 */
public interface TokenService {

    /**
     * Route to get access token from auth code.
     * @param tenantId microsoft tenant id
     * @param clientId microsoft client id
     * @param clientSecret microsoft client secret
     * @param grantType wanted grant type
     * @param code auth2 code
     * @param redirectUrl redirect uri
     * @return token response
     */
    @FormUrlEncoded
    @POST("/{tenantid}/oauth2/v2.0/token")
    Call<TokenResponse> getAccessTokenFromAuthCode(@Path("tenantid") String tenantId,
            @Field("client_id") String clientId, @Field("client_secret") String clientSecret,
            @Field("grant_type") String grantType, @Field("code") String code,
            @Field("redirect_uri") String redirectUrl);

    /**
     * Route to get access token from refresh token.
     * @param tenantId microsoft tenant id
     * @param clientId microsoft client id
     * @param clientSecret microsoft client secret
     * @param grantType wanted grant type
     * @param code auth2 code
     * @param redirectUrl redirect uri
     * @return token response
     */
    @FormUrlEncoded
    @POST("/{tenantid}/oauth2/v2.0/token")
    Call<TokenResponse> getAccessTokenFromRefreshToken(@Path("tenantid") String tenantId,
            @Field("client_id") String clientId, @Field("client_secret") String clientSecret,
            @Field("grant_type") String grantType, @Field("refresh_token") String code,
            @Field("redirect_uri") String redirectUrl);
}
