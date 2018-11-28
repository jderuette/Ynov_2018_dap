
package fr.ynov.dap.web.microsoft.auth;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;


/**
 * Interface TokenService
 * 
 * @author antod
 *
 */
public interface TokenService
{
  /**
   * Récupère l'accessToken grâce à l'authCode
   * 
   * @param tenantId
   * @param clientId
   * @param clientSecret
   * @param grantType
   * @param code
   * @param redirectUrl
   * @return
   */
  @FormUrlEncoded
  @POST("/{tenantid}/oauth2/v2.0/token")
  Call<TokenResponse> getAccessTokenFromAuthCode(@Path("tenantid") String tenantId, @Field("client_id") String clientId,
      @Field("client_secret") String clientSecret, @Field("grant_type") String grantType, @Field("code") String code,
      @Field("redirect_uri") String redirectUrl);

  /**
   * Récupère l'accessToken grâce au refreshToken
   * 
   * @param tenantId
   * @param clientId
   * @param clientSecret
   * @param grantType
   * @param code
   * @param redirectUrl
   * @return
   */
  @FormUrlEncoded
  @POST("/{tenantid}/oauth2/v2.0/token")
  Call<TokenResponse> getAccessTokenFromRefreshToken(@Path("tenantid") String tenantId,
      @Field("client_id") String clientId, @Field("client_secret") String clientSecret,
      @Field("grant_type") String grantType, @Field("refresh_token") String code,
      @Field("redirect_uri") String redirectUrl);
}
