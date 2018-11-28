package fr.ynov.dap.microsoft.auth;

import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Allows to skip unknown values during deserialization.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenResponse {
    /**
     * Token type.
     */
    @JsonProperty("token_type")
    private String tokenType;
    /**
     * Scope (like permissions with the API).
     */
    private String scope;
    /**
     * Expiration date of this tokenResponse.
     */
    @JsonProperty("expires_in")
    private int expiresIn;
    /**
     * Access token.
     */
    @JsonProperty("access_token")
    private String accessToken;
    /**
     * Refresh Token. Used to get a new token when that token will expire.
     */
    @JsonProperty("refresh_token")
    private String refreshToken;
    /**
     * Id of the token.
     */
    @JsonProperty("id_token")
    private String idToken;
    /**
     * Error.
     */
    private String error;
    /**
     * Description of the error.
     */
    @JsonProperty("error_description")
    private String errorDescription;
    /**
     * Error codes.
     */
    @JsonProperty("error_codes")
    private int[] errorCodes;
    /**
     * Date of the expiration of this TokenResponse.
     */
    private Date expirationTime;

    /**
     * tokenType getter.
     * @return token type.
     */
    public String getTokenType() {
        return tokenType;
    }

    /**
     * tokenType setter.
     * @param type token type.
     */
    public void setTokenType(final String type) {
        this.tokenType = type;
    }

    /**
     * Scope getter.
     * @return the scope of the token.
     */
    public String getScope() {
        return scope;
    }

    /**
     * Scope setter.
     * @param s token response scope.
     */
    public void setScope(final String s) {
        this.scope = s;
    }

    /**
     * Expiration of this token getter.
     * @return expiration of this token.
     */
    public int getExpiresIn() {
        return expiresIn;
    }

    /**
     * Expiration setter of this token.
     * @param expires duration in seconds until expiration.
     */
    public void setExpiresIn(final int expires) {
        this.expiresIn = expires;
        Calendar now = Calendar.getInstance();
        now.add(Calendar.SECOND, expiresIn);
        this.expirationTime = now.getTime();
    }

    /**
     * Access token getter.
     * @return the access token.
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Access token setter.
     * @param access access token.
     */
    public void setAccessToken(final String access) {
        this.accessToken = access;
    }

    /**
     * RefreshToken getter.
     * @return the refresh token.
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * RefreshToken setter.
     * @param refresh refresh token of the token.
     */
    public void setRefreshToken(final String refresh) {
        this.refreshToken = refresh;
    }

    /**
     * Token id getter.
     * @return Id of this token.
     */
    public String getIdToken() {
        return idToken;
    }

    /**
     * IdToken setter.
     * @param id id of the token.
     */
    public void setIdToken(final String id) {
        this.idToken = id;
    }

    /**
     * Error getter.
     * @return the error.
     */
    public String getError() {
        return error;
    }

    /**
     * Error setter.
     * @param e error.
     */
    public void setError(final String e) {
        this.error = e;
    }

    /**
     * ErrorDescription getter.
     * @return error description.
     */
    public String getErrorDescription() {
        return errorDescription;
    }

    /**
     * Error description setter.
     * @param description error description.
     */
    public void setErrorDescription(final String description) {
        this.errorDescription = description;
    }

    /**
     * Error codes getter.
     * @return error codes.
     */
    public int[] getErrorCodes() {
        return errorCodes;
    }

    /**
     * Error codes setter.
     * @param codes error codes.
     */
    public void setErrorCodes(final int[] codes) {
        this.errorCodes = codes;
    }

    /**
     * expiration time getter.
     * @return Date of the expiration of this token.
     */
    public Date getExpirationTime() {
        return expirationTime;
    }
}
