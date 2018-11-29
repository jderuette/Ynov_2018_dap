package fr.ynov.dap.service;

import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
/**
 * Recupère et accède aux informations relatives aux TokenReponse de microsoft.
 * @author abaracas
 *
 */
public class MicrosoftTokenResponseService {
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
	
	
	
}
