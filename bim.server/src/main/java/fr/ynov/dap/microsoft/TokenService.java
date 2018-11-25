package fr.ynov.dap.microsoft;

import fr.ynov.dap.data.microsoft.TokenResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Token service.
 * @author MBILLEMAZ
 *
 */
public interface TokenService {

    /**
     * Get token.
     * @param tenantId tenant
     * @param clientId client
     * @param clientSecret secret
     * @param grantType grant
     * @param code code
     * @param redirectUrl redirect
     * @return token
     */
    @FormUrlEncoded
    @POST("/{tenantid}/oauth2/v2.0/token")
    Call<TokenResponse> getAccessTokenFromAuthCode(@Path("tenantid") String tenantId,
            @Field("client_id") String clientId, @Field("client_secret") String clientSecret,
            @Field("grant_type") String grantType, @Field("code") String code,
            @Field("redirect_uri") String redirectUrl);

    @FormUrlEncoded
    @POST("/{tenantid}/oauth2/v2.0/token")
    Call<TokenResponse> getAccessTokenFromRefreshToken(@Path("tenantid") String tenantId,
            @Field("client_id") String clientId, @Field("client_secret") String clientSecret,
            @Field("grant_type") String grantType, @Field("refresh_token") String code,
            @Field("redirect_uri") String redirectUrl);
}