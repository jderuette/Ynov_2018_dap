/*
 * 
 */
package fr.ynov.dap.data.microsoft;

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

import fr.ynov.dap.Constant;

/**
 * The Class TokenResponse.
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenResponse {

	/** The id. */
    @Id
    @GeneratedValue
    private Integer id;

	/** The token type. */
    @Column
    @JsonProperty("token_type")
    private String tokenType;

	/** The scope. */
    @Column
    private String scope;

	/** The expires in. */
    @Column
    @JsonProperty("expires_in")
    private int expiresIn;

	/** The access token. */
    @Column(length = Constant.DATABASE_TOKEN_SIZE)
    @JsonProperty("access_token")
    private String accessToken;

	/** The refresh token. */
    @Column(length = Constant.DATABASE_TOKEN_SIZE)
    @JsonProperty("refresh_token")
    private String refreshToken;

	/** The id token. */
    @Column(length = Constant.DATABASE_TOKEN_SIZE)
    @JsonProperty("id_token")
    private String idToken;

	/** The account. */
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private MicrosoftAccount account;

	/** The error. */
    private String error;

	/** The error description. */
    @JsonProperty("error_description")
    private String errorDescription;

	/** The error codes. */
    @JsonProperty("error_codes")
    private int[] errorCodes;

	/** The expiration time. */
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
	 * @return the token type
	 */
    public String getTokenType() {
        return tokenType;
    }

	/**
	 * Sets the token type.
	 *
	 * @param val the new token type
	 */
    public void setTokenType(final String val) {
        this.tokenType = val;
    }

	/**
	 * Gets the expires in.
	 *
	 * @return the expires in
	 */
    public int getExpiresIn() {
        return expiresIn;
    }

	/**
	 * Sets the expires in.
	 *
	 * @param val the new expires in
	 */
    public void setExpiresIn(final int val) {
        this.expiresIn = val;
    }

	/**
	 * Gets the access token.
	 *
	 * @return the access token
	 */
    public String getAccessToken() {
        return accessToken;
    }

	/**
	 * Sets the access token.
	 *
	 * @param val the new access token
	 */
    public void setAccessToken(final String val) {
        this.accessToken = val;
    }

	/**
	 * Gets the refresh token.
	 *
	 * @return the refresh token
	 */
    public String getRefreshToken() {
        return refreshToken;
    }

	/**
	 * Sets the refresh token.
	 *
	 * @param val the new refresh token
	 */
    public void setRefreshToken(final String val) {
        this.refreshToken = val;
    }

	/**
	 * Gets the id token.
	 *
	 * @return the id token
	 */
    public String getIdToken() {
        return idToken;
    }

	/**
	 * Sets the id token.
	 *
	 * @param val the new id token
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
	 * @param val the new error
	 */
    public void setError(final String val) {
        this.error = val;
    }

	/**
	 * Gets the error description.
	 *
	 * @return the error description
	 */
    public String getErrorDescription() {
        return errorDescription;
    }

	/**
	 * Sets the error description.
	 *
	 * @param val the new error description
	 */
    public void setErrorDescription(final String val) {
        this.errorDescription = val;
    }

	/**
	 * Gets the error codes.
	 *
	 * @return the error codes
	 */
    public int[] getErrorCodes() {
        return errorCodes;
    }

	/**
	 * Sets the error codes.
	 *
	 * @param val the new error codes
	 */
    public void setErrorCodes(final int[] val) {
        this.errorCodes = val;
    }

	/**
	 * Gets the expiration time.
	 *
	 * @return the expiration time
	 */
    public Date getExpirationTime() {
        return expirationTime;
    }

	/**
	 * Sets the expiration time.
	 *
	 * @param val the new expiration time
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
	 * @param val the new account
	 */
    public void setAccount(final MicrosoftAccount val) {
        this.account = val;
    }

}
