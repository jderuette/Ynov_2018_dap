package fr.ynov.dap.dap.microsoft.auth;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Florian
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenResponse {

    /**
     * 
     */
    private final static int COLUMN = 10000;

    /**.
     * Déclaration de l'Id
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**.
     * Déclaration de tokenType
     */
    @JsonProperty("token_type")
    private String tokenType;
    /**.
     * Déclaration de scope
     */
    private String scope;
    /**.
     * Déclaration de expiresIn
     */
    @JsonProperty("expires_in")
    private int expiresIn;
    /**.
     * Déclaration de accessToken
     */
    @Column(length = COLUMN)
    @JsonProperty("access_token")
    private String accessToken;
    /**.
     * Déclaration de refreshToken
     */
    @Column(length = COLUMN)
    @JsonProperty("refresh_token")
    private String refreshToken;
    /**.
     * Déclaration de idToken
     */
    @Column(length = COLUMN)
    @JsonProperty("id_token")
    private String idToken;
    /**.
     * Déclaration de error
     */
    private String error;
    /**.
     * Déclaration de errorDescription
     */
    @JsonProperty("error_description")
    private String errorDescription;
    /**.
     * Déclaration de errorCodes
     */
    @JsonProperty("error_codes")
    private int[] errorCodes;
    /**.
     * Déclaration de expirationTime
     */
    private Date expirationTime;

    /**
     * @return tokenType
     */
    public String getTokenType() {
        return tokenType;
    }

    /**
     * @param theTokenType Modification de la valeur
     */
    public void setTokenType(final String theTokenType) {
        this.tokenType = theTokenType;
    }

    /**
     * @return scope
     */
    public String getScope() {
        return scope;
    }

    /**
     * @param theScope Modification de la valeur
     */
    public void setScope(final String theScope) {
        this.scope = theScope;
    }

    /**
     * @return expiresIn
     */
    public int getExpiresIn() {
        return expiresIn;
    }

    /**
     * @param theExpiresIn Modification de la valeur
     */
    public void setExpiresIn(final int theExpiresIn) {
        this.expiresIn = theExpiresIn;
        Calendar now = Calendar.getInstance();
        now.add(Calendar.SECOND, expiresIn);
        this.expirationTime = now.getTime();
    }

    /**
     * @return accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * @param theAccessToken Modification de la valeur
     */
    public void setAccessToken(final String theAccessToken) {
        this.accessToken = theAccessToken;
    }

    /**
     * @return refreshToken
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * @param theRefreshToken Modification de la valeur
     */
    public void setRefreshToken(final String theRefreshToken) {
        this.refreshToken = theRefreshToken;
    }

    /**
     * @return idToken
     */
    public String getIdToken() {
        return idToken;
    }

    /**
     * @param theIdToken Modification de la valeur
     */
    public void setIdToken(final String theIdToken) {
        this.idToken = theIdToken;
    }

    /**
     * @return error
     */
    public String getError() {
        return error;
    }

    /**
     * @param theError Modification de la valeur
     */
    public void setError(final String theError) {
        this.error = theError;
    }

    /**
     * @return errorDescription
     */
    public String getErrorDescription() {
        return errorDescription;
    }

    /**
     * @param theErrorDescription Modification de la valeur
     */
    public void setErrorDescription(final String theErrorDescription) {
        this.errorDescription = theErrorDescription;
    }

    /**
     * @return errorCodes
     */
    public int[] getErrorCodes() {
        return errorCodes;
    }

    /**
     * @param theErrorCodes Modification de la valeur
     */
    public void setErrorCodes(final int[] theErrorCodes) {
        this.errorCodes = theErrorCodes;
    }

    /**
     * @return expirationTime
     */
    public Date getExpirationTime() {
        return expirationTime;
    }
}
