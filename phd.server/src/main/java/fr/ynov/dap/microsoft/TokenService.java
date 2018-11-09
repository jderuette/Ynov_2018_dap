package fr.ynov.dap.microsoft;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 *
 * @author Dom
 *
 */
public interface TokenService {

    /**
     *
     * @param tenantId .
     * @param clientId .
     * @param clientSecret .
     * @param grantType .
     * @param code .
     * @param redirectUrl .
     * @return .
     */
    @FormUrlEncoded
    @POST("/{tenantid}/oauth2/v2.0/token")
    Call<TokenResponse> getAccessTokenFromAuthCode(@Path("tenantid") String tenantId,
            @Field("client_id") String clientId, @Field("client_secret") String clientSecret,
            @Field("grant_type") String grantType, @Field("code") String code,
            @Field("redirect_uri") String redirectUrl);
}
