package fr.ynov.dap.microsoft.data;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * The Class TokenResponse.
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenResponse {

    /**
     * Unique.
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * Token type.
     */
    @Column
    @JsonProperty("token_type")
    private String tokenType;

    /**
     * Scope.
     */
    @Column
    private String scope;

    /**
     * Expires in.
     */
    @Column
    @JsonProperty("expires_in")
    private int expiresIn;

    /**
     * Access token.
     */
    @Column(length = 5000)
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * Refresh token.
     */
    @Column(length = 5000)
    @JsonProperty("refresh_token")
    private String refreshToken;

    /**
     * Id token.
     */
    @Column(length = 5000)
    @JsonProperty("id_token")
    private String idToken;

    /**
     * Microsoft Account column.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private MicrosoftAccount account;

    /**
     * Error.
     */
    private String error;

    /**
     * Error description.
     */
    @JsonProperty("error_description")
    private String errorDescription;

    /**
     * Error codes.
     */
    @JsonProperty("error_codes")
    private int[] errorCodes;

    /**
     * Expiration Time.
     */
    @JsonProperty("ext_expires_in")
    private Date expirationTime;

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Gets the token type.
     *
     * @return the tokenType
     */
    public String getTokenType() {
        return tokenType;
    }

    /**
     * Sets the token type.
     *
     * @param val the tokenType to set
     */
    public void setTokenType(final String val) {
        this.tokenType = val;
    }

    /**
     * Gets the expires in.
     *
     * @return the expiresIn
     */
    public int getExpiresIn() {
        return expiresIn;
    }

    /**
     * Sets the expires in.
     *
     * @param val the expiresIn to set
     */
    public void setExpiresIn(final int val) {
        this.expiresIn = val;
    }

    /**
     * Gets the access token.
     *
     * @return the accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Sets the access token.
     *
     * @param val the accessToken to set
     */
    public void setAccessToken(final String val) {
        this.accessToken = val;
    }

    /**
     * Gets the refresh token.
     *
     * @return the refreshToken
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Sets the refresh token.
     *
     * @param val the refreshToken to set
     */
    public void setRefreshToken(final String val) {
        this.refreshToken = val;
    }

    /**
     * Gets the id token.
     *
     * @return the idToken
     */
    public String getIdToken() {
        return idToken;
    }

    /**
     * Sets the id token.
     *
     * @param val the idToken to set
     */
    public void setIdToken(final String val) {
        this.idToken = val;
    }

    /**
     * Gets the error.
     *
     * @return the error
     */
    public String getError() {
        return error;
    }

    /**
     * Sets the error.
     *
     * @param val the error to set
     */
    public void setError(final String val) {
        this.error = val;
    }

    /**
     * Gets the error description.
     *
     * @return the errorDescription
     */
    public String getErrorDescription() {
        return errorDescription;
    }

    /**
     * Sets the error description.
     *
     * @param val the errorDescription to set
     */
    public void setErrorDescription(final String val) {
        this.errorDescription = val;
    }

    /**
     * Gets the error codes.
     *
     * @return the errorCodes
     */
    public int[] getErrorCodes() {
        return errorCodes;
    }

    /**
     * Sets the error codes.
     *
     * @param val the errorCodes to set
     */
    public void setErrorCodes(final int[] val) {
        this.errorCodes = val;
    }

    /**
     * Gets the expiration time.
     *
     * @return the expirationTime
     */
    public Date getExpirationTime() {
        return expirationTime;
    }

    /**
     * Sets the expiration time.
     *
     * @param val the expirationTime to set
     */
    public void setExpirationTime(final Date val) {
        this.expirationTime = val;
    }

    /**
     * Gets the account.
     *
     * @return the account
     */
    public MicrosoftAccount getAccount() {
        return account;
    }

    /**
     * Sets the account.
     *
     * @param val the account to set
     */
    public void setAccount(final MicrosoftAccount val) {
        this.account = val;
    }

	/**
	 * Gets the scope.
	 *
	 * @return the scope
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * Sets the scope.
	 *
	 * @param scope the new scope
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}
    
    

}
