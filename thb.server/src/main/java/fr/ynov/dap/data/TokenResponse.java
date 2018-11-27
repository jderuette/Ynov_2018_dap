package fr.ynov.dap.data;

import java.util.Calendar;
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

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenResponse {
	
	@Id
    @GeneratedValue
    private Integer id;
	
	@OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private MicrosoftAccount account;
	
	@JsonProperty("token_type")
	private String tokenType;
	
	private String scope;
	
	@JsonProperty("expires_in")
	private int expiresIn;
	
	@Column(length = 5000)
	@JsonProperty("access_token")
	private String accessToken;
	
	@Column(length = 5000)
	@JsonProperty("refresh_token")
	private String refreshToken;
	
	@Column(length = 5000)
	@JsonProperty("id_token")
	private String idToken;
	
	private String error;
	
	@JsonProperty("error_description")
	private String errorDescription;
	
	@JsonProperty("error_codes")
	private int[] errorCodes;
	
	private Date expirationTime;

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
		Calendar now = Calendar.getInstance();
		now.add(Calendar.SECOND, expiresIn);
		this.expirationTime = now.getTime();
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getIdToken() {
		return idToken;
	}

	public void setIdToken(String idToken) {
		this.idToken = idToken;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public int[] getErrorCodes() {
		return errorCodes;
	}

	public void setErrorCodes(int[] errorCodes) {
		this.errorCodes = errorCodes;
	}

	public Date getExpirationTime() {
		return expirationTime;
	}
}