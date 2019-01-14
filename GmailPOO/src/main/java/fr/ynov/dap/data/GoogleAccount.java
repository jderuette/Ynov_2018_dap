/**
 * 
 */
package fr.ynov.dap.data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author acer
 *
 */
@Entity
@DiscriminatorValue("google")
public class GoogleAccount extends Account{
	 private String accessToken;
	 private String tokenType;
	 private Long expiresInSeconds;
	 private String refreshToken;
	 private String scope;
	 

	/**
	 * 
	 */
	public GoogleAccount() {
		super();
	}

	
	/**
	 * @param accountName
	 * @param adrMail
	 * @param owner
	 * @param accessToken
	 * @param tokenType
	 * @param expiresInSeconds
	 * @param refreshToken
	 * @param scope
	 */
	public GoogleAccount(String accountName, String adrMail, AppUser owner,String accessToken, String tokenType, Long expiresInSeconds, String refreshToken,
			String scope) {
		super(accountName, adrMail, owner);
		this.accessToken = accessToken;
		this.tokenType = tokenType;
		this.expiresInSeconds = expiresInSeconds;
		this.refreshToken = refreshToken;
		this.scope = scope;
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
	 * @return the expiresInSeconds
	 */
	public Long getExpiresInSeconds() {
		return expiresInSeconds;
	}
	/**
	 * @param expiresInSeconds the expiresInSeconds to set
	 */
	public void setExpiresInSeconds(Long expiresInSeconds) {
		this.expiresInSeconds = expiresInSeconds;
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
	
}
