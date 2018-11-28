package fr.ynov.dap.microsoft.contract;

import fr.ynov.dap.microsoft.data.TokenResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * The Interface TokenService.
 */
public interface TokenService {

  /**
   * Gets the access token from auth code.
   *
   * @param tenantId the tenant id
   * @param clientId the client id
   * @param clientSecret the client secret
   * @param grantType the grant type
   * @param code the code
   * @param redirectUrl the redirect url
   * @return the access token from auth code
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
   * Gets the access token from refresh token.
   *
   * @param tenantId the tenant id
   * @param clientId the client id
   * @param clientSecret the client secret
   * @param grantType the grant type
   * @param code the code
   * @param redirectUrl the redirect url
   * @return the access token from refresh token
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