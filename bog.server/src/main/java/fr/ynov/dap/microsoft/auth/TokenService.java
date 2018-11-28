package fr.ynov.dap.microsoft.auth;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @author Mon_PC
 */
public interface TokenService {
    /**
     * @param tenantId .
     * @param clientId .
     * @param clientSecret .
     * @param grantType .
     * @param code .
     * @param redirectUrl .
     * @return accessToken
     */
    @FormUrlEncoded
    @POST("/{tenantid}/oauth2/v2.0/token")
    Call<TokenResponse> getAccessTokenFromAuthCode(@Path("tenantid") String tenantId,
            @Field("client_id") String clientId, @Field("client_secret") String clientSecret,
            @Field("grant_type") String grantType, @Field("code") String code,
            @Field("redirect_uri") String redirectUrl);

    /**
     * @param tenantId identifiant tenant
     * @param clientId identifiant client
     * @param clientSecret secret client
     * @param grantType droit
     * @param code .
     * @param redirectUrl url de redirection
     * @return RefreshToken
     */
    @FormUrlEncoded
    @POST("/{tenantid}/oauth2/v2.0/token")
    Call<TokenResponse> getAccessTokenFromRefreshToken(@Path("tenantid") String tenantId,
            @Field("client_id") String clientId, @Field("client_secret") String clientSecret,
            @Field("grant_type") String grantType, @Field("refresh_token") String code,
            @Field("redirect_uri") String redirectUrl);
}
