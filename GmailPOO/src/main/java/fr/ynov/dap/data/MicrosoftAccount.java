package fr.ynov.dap.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("microsoft")
public class MicrosoftAccount extends Account {
    private String tokenType;
    private String scope;
    private int expiresIn;
    @Column(columnDefinition = "LONGVARCHAR")
    private String idToken;
    private String tenantId;
    private Date modifierLe;
    private Date ExpirationTime;
    @Column(columnDefinition = "LONGVARCHAR")
    private String accessToken;
    @Column(columnDefinition = "LONGVARCHAR")
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
    public MicrosoftAccount(String accountName, String adrMail, AppUser owner, String tokenType, String scope,
            int expiresIn, String idToken, String tenantId, Date modifierLe, Date expirationTime, String accessToken,
            String refreshToken) {
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
     * @param newExpirationTime the expirationTime to set
     */
    public void setExpirationTime(Date newExpirationTime) {
        ExpirationTime = newExpirationTime;
    }

    /**
     * @return the tokenType
     */
    public String getTokenType() {
        return tokenType;
    }

    /**
     * @param newTokenType the tokenType to set
     */
    public void setTokenType(String newTokenType) {
        this.tokenType = newTokenType;
    }

    /**
     * @return the scope
     */
    public String getScope() {
        return scope;
    }

    /**
     * @param newScope the scope to set
     */
    public void setScope(String newScope) {
        this.scope = newScope;
    }

    /**
     * @return the expiresIn
     */
    public int getExpiresIn() {
        return expiresIn;
    }

    /**
     * @param newExpiresIn the expiresIn to set
     */
    public void setExpiresIn(int newExpiresIn) {
        this.expiresIn = newExpiresIn;
    }

    /**
     * @return the idToken
     */
    public String getIdToken() {
        return idToken;
    }

    /**
     * @param newIdToken the idToken to set
     */
    public void setIdToken(String newIdToken) {
        this.idToken = newIdToken;
    }

    /**
     * @return the tenantId
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * @param newTenantId the tenantId to set
     */
    public void setTenantId(String newTenantId) {
        this.tenantId = newTenantId;
    }

    /**
     * @return the modifierLe
     */
    public Date getModifierLe() {
        return modifierLe;
    }

    /**
     * @param newModifierLe the modifierLe to set
     */
    public void setModifierLe(Date newModifierLe) {
        this.modifierLe = newModifierLe;
    }

    /**
     * @return the accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * @param newAccessToken the accessToken to set
     */
    public void setAccessToken(String newAccessToken) {
        this.accessToken = newAccessToken;
    }

    /**
     * @return the refreshToken
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * @param newRefreshToken the refreshToken to set
     */
    public void setRefreshToken(String newRefreshToken) {
        this.refreshToken = newRefreshToken;
    }

}
