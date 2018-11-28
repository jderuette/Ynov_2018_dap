package fr.ynov.dap.microsoft.auth;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Retrofit interface of the Token service. It will be used to send request to the Microsoft Graph API.
 */
public interface TokenService {

    /**
     * Token service Interface.
     * @param tenantId tenant id.
     * @param clientId client id
     * @param clientSecret client secret
     * @param grantType grant type
     * @param code code
     * @param redirectUrl redirect url
     * @return form url
     */
  @FormUrlEncoded
  @POST("/{tenantid}/oauth2/v2.0/token")
  Call<TokenResponse> getAccessTokenFromAuthCode(
    @Path("tenantid") String tenantId,
    @Field("client_id") String clientId,
    @Field("client_secret") String clientSecret,
    @Field("grant_type") String grantType,
    @Field("code") String code,
    @Field("redirect_uri") String redirectUrl
  );

  /**
   * Token service Interface.
   * @param tenantId tenant id.
   * @param clientId client id
   * @param clientSecret client secret
   * @param grantType grant type
   * @param code code
   * @param redirectUrl redirect url
   * @return form url
   */
  @FormUrlEncoded
  @POST("/{tenantid}/oauth2/v2.0/token")
  Call<TokenResponse> getAccessTokenFromRefreshToken(
    @Path("tenantid") String tenantId,
    @Field("client_id") String clientId,
    @Field("client_secret") String clientSecret,
    @Field("grant_type") String grantType,
    @Field("refresh_token") String code,
    @Field("redirect_uri") String redirectUrl
  );
}
