package fr.ynov.dap.services.microsoft;

import fr.ynov.dap.models.microsoft.MicrosoftTokenResponse;
import retrofit2.Call;
import retrofit2.http.*;

public interface TokenService {

    @FormUrlEncoded
    @POST("/{tenantid}/oauth2/v2.0/token")
    Call<MicrosoftTokenResponse> getAccessTokenFromAuthCode(
            @Path("tenantid") String tenantId,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("grant_type") String grantType,
            @Field("code") String code,
            @Field("redirect_uri") String redirectUrl
    );

    @FormUrlEncoded
    @POST("/{tenantid}/oauth2/v2.0/token")
    Call<MicrosoftTokenResponse> getAccessTokenFromRefreshToken(
            @Path("tenantid") String tenantId,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("grant_type") String grantType,
            @Field("refresh_token") String code,
            @Field("redirect_uri") String redirectUrl
    );
}
