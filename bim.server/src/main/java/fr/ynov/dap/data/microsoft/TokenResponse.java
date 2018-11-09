package fr.ynov.dap.data.microsoft;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * microsoft token response
 * @author MBILLEMAZ
 *
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenResponse {

    /**
     * base id.
     */
    @Id
    @GeneratedValue
    private Integer id;
    /**
     * token.
     */
    @JsonProperty("token_type")
    private String tokenType;

    /**
     * scope.
     */
    private String scope;
    /**
     * expiration date.
     */
    @JsonProperty("expires_in")
    private int expiresIn;
    /**
     * access token.
     */
    @Column(length=2048)
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * refresh token.
     */
    @Column(length=2048)
    @JsonProperty("refresh_token")
    private String refreshToken;

    /**
     * id token.
     */
    @Column(length=2048)
    @JsonProperty("id_token")
    private String idToken;
    /**
     * error.
     */
    private String error;
    /**
     * error desc.
     */
    @JsonProperty("error_description")
    private String errorDescription;

    /**
     * error code.
     */
    @JsonProperty("error_codes")
    private int[] errorCodes;

    /**
     * expiration.
     */
    private Date expirationTime;

    /**
     * @return the tokenType
     */
    public String getTokenType() {
        return tokenType;
    }

    /**
     * @param tokenType the tokenType to set
     */
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    /**
     * @return the scope
     */
    public String getScope() {
        return scope;
    }

    /**
     * @param scope the scope to set
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * @return the expiresIn
     */
    public int getExpiresIn() {
        return expiresIn;
    }

    /**
     * @param expiresIn the expiresIn to set
     */
    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
        Calendar now = Calendar.getInstance();
        now.add(Calendar.SECOND, expiresIn);
        this.expirationTime = now.getTime();
    }

    /**
     * @return the accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * @param accessToken the accessToken to set
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * @return the refreshToken
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * @param refreshToken the refreshToken to set
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * @return the idToken
     */
    public String getIdToken() {
        return idToken;
    }

    /**
     * @param idToken the idToken to set
     */
    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    /**
     * @return the error
     */
    public String getError() {
        return error;
    }

    /**
     * @param error the error to set
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * @return the errorDescription
     */
    public String getErrorDescription() {
        return errorDescription;
    }

    /**
     * @param errorDescription the errorDescription to set
     */
    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    /**
     * @return the errorCodes
     */
    public int[] getErrorCodes() {
        return errorCodes;
    }

    /**
     * @param errorCodes the errorCodes to set
     */
    public void setErrorCodes(int[] errorCodes) {
        this.errorCodes = errorCodes;
    }

    /**
     * @return the expirationTime
     */
    public Date getExpirationTime() {
        return expirationTime;
    }

    /**
     * @param expirationTime the expirationTime to set
     */
    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "TokenResponse [tokenType=" + tokenType + ", scope=" + scope + ", expiresIn=" + expiresIn
                + ", accessToken=" + accessToken + ", refreshToken=" + refreshToken + ", idToken=" + idToken
                + ", error=" + error + ", errorDescription=" + errorDescription + ", errorCodes="
                + Arrays.toString(errorCodes) + ", expirationTime=" + expirationTime + "]";
    }

}
