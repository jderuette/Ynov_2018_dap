package fr.ynov.dap.microsoft.auth;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mon_PC
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenResponse {

    /**.
     * propriété length
     */
    //TODO bog by Djer |JavaDoc| Nom un peu plus claire ? Au moins une Javadoc plus claire ?
    private static final int LENGTH = 1000000;

    /**.
    * Propriété id
    */
    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**.
     * @param newId correspondant nouveau id
     */
    public void setId(final Integer newId) {
        this.id = newId;
    }

    /**.
     * propriété token type
     */
    @JsonProperty("token_type")
    private String tokenType;
    /**.
     * propriété scope
     */
    private String scope;
    /**.
     * propriété expiresIn
     */
    @JsonProperty("expires_in")
    private int expiresIn;
    /**.
     * propriété access_token
     */
    @Column(length = LENGTH)
    @JsonProperty("access_token")
    private String accessToken;
    /**.
     * propriété refresh_token
     */
    @Column(length = LENGTH)
    @JsonProperty("refresh_token")
    private String refreshToken;
    /**.
     * propriété id_token
     */
    @Column(length = LENGTH)
    @JsonProperty("id_token")
    private String idToken;
    /**.
     * propriété error
     */
    private String error;
    /**.
     * propriété error_description
     */
    @JsonProperty("error_description")
    private String errorDescription;
    /**.
     * propriété error_codes
     */
    @JsonProperty("error_codes")
    private int[] errorCodes;
    /**.
     * propriété expirationTime
     */
    private Date expirationTime;

    /**
     * @return tokentype
     */
    public String getTokenType() {
        return tokenType;
    }

    /**
     * @param newTokenType set new tokentype
     */
    public void setTokenType(final String newTokenType) {
        this.tokenType = newTokenType;
    }

    /**
     * @return scope
     */
    public String getScope() {
        return scope;
    }

    /**
     * @param newScope set new scope
     */
    public void setScope(final String newScope) {
        this.scope = newScope;
    }

    /**
     * @return expiresin
     */
    public int getExpiresIn() {
        return expiresIn;
    }

    /**
     * @param newExpiresIn set new expiresin
     */
    public void setExpiresIn(final int newExpiresIn) {
        this.expiresIn = newExpiresIn;
        Calendar now = Calendar.getInstance();
        now.add(Calendar.SECOND, expiresIn);
        this.expirationTime = now.getTime();
    }

    /**
     * @return accesstoken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * @param newAccessToken set new accesstoken
     */
    public void setAccessToken(final String newAccessToken) {
        this.accessToken = newAccessToken;
    }

    /**
     * @return refreshtoken
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * @param newRefreshToken set new refreshtoken
     */
    public void setRefreshToken(final String newRefreshToken) {
        this.refreshToken = newRefreshToken;
    }

    /**
     * @return idToken
     */
    public String getIdToken() {
        return idToken;
    }

    /**
     * @param newIdToken set new IdToken
     */
    public void setIdToken(final String newIdToken) {
        this.idToken = newIdToken;
    }

    /**
     * @return error
     */
    public String getError() {
        return error;
    }

    /**
     * @param newError set new error
     */
    public void setError(final String newError) {
        this.error = newError;
    }

    /**
     * @return errorDescription
     */
    public String getErrorDescription() {
        return errorDescription;
    }

    /**
     * @param newErrorDescription set new errorDescription
     */
    public void setErrorDescription(final String newErrorDescription) {
        this.errorDescription = newErrorDescription;
    }

    /**
     * @return errorCode
     */
    public int[] getErrorCodes() {
        return errorCodes;
    }

    /**
     * @param newErrorCodes set new errorCodes
     */
    public void setErrorCodes(final int[] newErrorCodes) {
        this.errorCodes = newErrorCodes;
    }

    /**
     * @return expirationTime
     */
    public Date getExpirationTime() {
        return expirationTime;
    }
}
