package fr.ynov.dap.contract;

import fr.ynov.dap.data.TokenResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Service to talk to Microsoft API.
 * @author Kévin Sibué
 *
 */
public interface TokenService {

    /**
     * Get token.
     * @param tenantId TenantId
     * @param clientId ClientId
     * @param clientSecret ClientSecret
     * @param grantType grantType
     * @param code code
     * @param redirectUrl Redirect URL
     * @return Token Responses
     */
    @FormUrlEncoded
    @POST("/{tenantid}/oauth2/v2.0/token")
    Call<TokenResponse> getAccessTokenFromAuthCode(@Path("tenantid") String tenantId,
            @Field("client_id") String clientId, @Field("client_secret") String clientSecret,
            @Field("grant_type") String grantType, @Field("code") String code,
            @Field("redirect_uri") String redirectUrl);

    /**
     * Refresh token.
     * @param tenantId TenantId
     * @param clientId ClientId
     * @param clientSecret ClientSecret
     * @param grantType GrantType
     * @param code Code
     * @param redirectUrl Redirect Url
     * @return Refreshed token
     */
    @FormUrlEncoded
    @POST("/{tenantid}/oauth2/v2.0/token")
    Call<TokenResponse> getAccessTokenFromRefreshToken(@Path("tenantid") String tenantId,
            @Field("client_id") String clientId, @Field("client_secret") String clientSecret,
            @Field("grant_type") String grantType, @Field("refresh_token") String code,
            @Field("redirect_uri") String redirectUrl);

}
