package fr.ynov.dap.dap.microsoft.services.CallService;

import fr.ynov.dap.dap.model.TokenResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 *
 * @author David_tepoche
 *
 */
public interface TokenService {
    /**
     * call the auth2 for the microsoft token.
     *
     * @param tenantId     key of the token of the account
     * @param clientId     id of the appli
     * @param clientSecret application secret
     * @param grantType    type of request ( refresh or authorisation )
     * @param code         authorization code get from the first call
     * @param redirectUrl  url redirect st in api microsoft
     * @return return the token response from microsoft
     */
    @FormUrlEncoded
    @POST("/{tenantid}/oauth2/v2.0/token")
    Call<TokenResponse> getAccessTokenFromAuthCode(@Path("tenantid") String tenantId,
            @Field("client_id") String clientId, @Field("client_secret") String clientSecret,
            @Field("grant_type") String grantType, @Field("code") String code,
            @Field("redirect_uri") String redirectUrl);

    /**
     * call the auth2 for trefresh the microsoft token.
     *
     * @param tenantId     key of the token of the account
     * @param clientId     id of the appli
     * @param clientSecret application secret
     * @param grantType    type of request ( refresh or authorisation )
     * @param code         authorization code get from the first call
     * @param redirectUrl  url redirect st in api microsoft
     * @return the call of the token response
     */
    @FormUrlEncoded
    @POST("/{tenantid}/oauth2/v2.0/token")
    Call<TokenResponse> getAccessTokenFromRefreshToken(@Path("tenantid") String tenantId,
            @Field("client_id") String clientId, @Field("client_secret") String clientSecret,
            @Field("grant_type") String grantType, @Field("refresh_token") String code,
            @Field("redirect_uri") String redirectUrl);

}
