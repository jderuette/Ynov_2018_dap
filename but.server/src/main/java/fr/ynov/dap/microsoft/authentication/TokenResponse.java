package fr.ynov.dap.microsoft.authentication;

import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Reponse token of microsoft.
 * @author thibault
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenResponse {
    /**
     * Token type.
     */
    @JsonProperty("token_type")
    private String tokenType;
    /**
     * Scopes of token.
     */
    private String scope;

    /**
     * Time before expiration token.
     */
    @JsonProperty("expires_in")
    private int expiresIn;

    /**
     * Access token.
     */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * Refresh token.
     */
    @JsonProperty("refresh_token")
    private String refreshToken;

    /**
     * Id token.
     */
    @JsonProperty("id_token")
    private String idToken;

    /**
     * Error response.
     */
    private String error;

    /**
     * Error description.
     */
    @JsonProperty("error_description")
    private String errorDescription;

    /**
     * Error code.
     */
    @JsonProperty("error_codes")
    private int[] errorCodes;

    /**
     * Date of expiration of token.
     */
    private Date expirationTime;

    /**
     * @return the tokenType
     */
    public String getTokenType() {
        return tokenType;
    }

    /**
     * @param tokenTypeToSet the tokenType to set
     */
    public void setTokenType(final String tokenTypeToSet) {
        this.tokenType = tokenTypeToSet;
    }

    /**
     * @return the scope
     */
    public String getScope() {
        return scope;
    }

    /**
     * @param scopeToSet the scope to set
     */
    public void setScope(final String scopeToSet) {
        this.scope = scopeToSet;
    }

    /**
     * @return the accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * @param accessTokenToSet the accessToken to set
     */
    public void setAccessToken(final String accessTokenToSet) {
        this.accessToken = accessTokenToSet;
    }

    /**
     * @return the refreshToken
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * @param refreshTokenToSet the refreshToken to set
     */
    public void setRefreshToken(final String refreshTokenToSet) {
        this.refreshToken = refreshTokenToSet;
    }

    /**
     * @return the idToken
     */
    public String getIdToken() {
        return idToken;
    }

    /**
     * @param idTokenToSet the idToken to set
     */
    public void setIdToken(final String idTokenToSet) {
        this.idToken = idTokenToSet;
    }

    /**
     * @return the error
     */
    public String getError() {
        return error;
    }

    /**
     * @param errorToSet the error to set
     */
    public void setError(final String errorToSet) {
        this.error = errorToSet;
    }

    /**
     * @return the errorDescription
     */
    public String getErrorDescription() {
        return errorDescription;
    }

    /**
     * @param errorDescriptionToSet the errorDescription to set
     */
    public void setErrorDescription(final String errorDescriptionToSet) {
        this.errorDescription = errorDescriptionToSet;
    }

    /**
     * @return the errorCodes
     */
    public int[] getErrorCodes() {
        return errorCodes;
    }

    /**
     * @param errorCodesToSet the errorCodes to set
     */
    public void setErrorCodes(final int[] errorCodesToSet) {
        this.errorCodes = errorCodesToSet;
    }

    /**
     * @return the expirationTime
     */
    public Date getExpirationTime() {
        return expirationTime;
    }

    /**
     * @param expirationTimeToSet the expirationTime to set
     */
    public void setExpirationTime(final Date expirationTimeToSet) {
        this.expirationTime = expirationTimeToSet;
    }

    /**
     * @return the expiresIn
     */
    public int getExpiresIn() {
        return expiresIn;
    }

    /**
     * @param expiresInToSet the expiresIn to set
     */
    public void setExpiresIn(final int expiresInToSet) {
        this.expiresIn = expiresInToSet;
        Calendar now = Calendar.getInstance();
        now.add(Calendar.SECOND, expiresInToSet);
        this.expirationTime = now.getTime();
    }
}
