package fr.ynov.dap.data;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
@Entity
@DiscriminatorValue("microsoft")
public class MicrosoftAccount extends Account  {
	private String tokenType;
	private String scope;
	private int expiresIn;
	@Column(columnDefinition= "LONGVARCHAR")
	private String idToken;
	private String tenantId;
	private Date modifierLe; 
	private Date ExpirationTime;
	@Column(columnDefinition= "LONGVARCHAR")
	private String accessToken;
	@Column(columnDefinition= "LONGVARCHAR")
	private String refreshToken;
		/**
	 * 
	 */
	public MicrosoftAccount() {
		super();
		
	}

	
	/**
		 * @param tokenType
		 * @param scope
		 * @param expiresIn
		 * @param idToken
		 * @param tenantId
		 * @param modifierLe
		 * @param expirationTime
		 * @param accessToken
		 * @param refreshToken
		 */
		public MicrosoftAccount(String accountName,String adrMail,AppUser owner,String tokenType, String scope, int expiresIn, String idToken, String tenantId,
				Date modifierLe, Date expirationTime, String accessToken, String refreshToken) {
			super(accountName, adrMail, owner);
			this.tokenType = tokenType;
			this.scope = scope;
			this.expiresIn = expiresIn;
			this.idToken = idToken;
			this.tenantId = tenantId;
			this.modifierLe = modifierLe;
			this.ExpirationTime = expirationTime;
			this.accessToken = accessToken;
			this.refreshToken = refreshToken;
		}


	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @return the expirationTime
	 */
	public Date getExpirationTime() {
		return ExpirationTime;
	}
	/**
	 * @param expirationTime the expirationTime to set
	 */
	public void setExpirationTime(Date expirationTime) {
		ExpirationTime = expirationTime;
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
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
	}
	/**
	 * @param tenantId the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	/**
	 * @return the modifierLe
	 */
	public Date getModifierLe() {
		return modifierLe;
	}
	/**
	 * @param modifierLe the modifierLe to set
	 */
	public void setModifierLe(Date modifierLe) {
		this.modifierLe = modifierLe;
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
	
	


}
