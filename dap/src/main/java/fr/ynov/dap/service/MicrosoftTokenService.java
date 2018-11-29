package fr.ynov.dap.service;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Permet l'accès aux Token.
 * @author abaracas
 *
 */
public interface MicrosoftTokenService {

	@FormUrlEncoded
	@POST("/{tenantid}/oauth2/v2.0/token")
	Call<MicrosoftTokenResponseService> getAccessTokenFromAuthCode(
		@Path("tenantid") String tenantId,
		@Field("client_id") String clientId,
		@Field("client_secret") String clientSecret,
		@Field("grant_type") String grantType,
		@Field("code") String code,
		@Field("redirect_uri") String redirectUrl
	);
	
	@FormUrlEncoded
	@POST("/{tenantid}/oauth2/v2.0/token")
	Call<MicrosoftTokenResponseService> getAccessTokenFromRefreshToken(
		@Path("tenantid") String tenantId,
		@Field("client_id") String clientId,
		@Field("client_secret") String clientSecret,
		@Field("grant_type") String grantType,
		@Field("refresh_token") String code,
		@Field("redirect_uri") String redirectUrl
	);
}
