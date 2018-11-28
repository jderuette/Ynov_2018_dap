package fr.ynov.dap.data.microsoft;

import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenResponse {
    @JsonProperty("token_type")
    private String tokenType;
    private String scope;
    @JsonProperty("expires_in")
    private int expiresIn;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("id_token")
    private String idToken;
    private String error;
    @JsonProperty("error_description")
    private String errorDescription;
    @JsonProperty("error_codes")
    private int[] errorCodes;
    private Date expirationTime;

    public final String getTokenType() {
        return tokenType;
    }
    public final void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
    public final String getScope() {
        return scope;
    }
    public final void setScope(String scope) {
        this.scope = scope;
    }
    public final int getExpiresIn() {
        return expiresIn;
    }
    public final void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
        Calendar now = Calendar.getInstance();
        now.add(Calendar.SECOND, expiresIn);
        this.expirationTime = now.getTime();
    }
    public final String getAccessToken() {
        return accessToken;
    }
    public final void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public final String getRefreshToken() {
        return refreshToken;
    }
    public final void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    public final String getIdToken() {
        return idToken;
    }
    public final void setIdToken(String idToken) {
        this.idToken = idToken;
    }
    public final String getError() {
        return error;
    }
    public final void setError(String error) {
        this.error = error;
    }
    public final String getErrorDescription() {
        return errorDescription;
    }
    public final void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
    public final int[] getErrorCodes() {
        return errorCodes;
    }
    public final void setErrorCodes(int[] errorCodes) {
        this.errorCodes = errorCodes;
    }
    public final Date getExpirationTime() {
        return expirationTime;
    }
}