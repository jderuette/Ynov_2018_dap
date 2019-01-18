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
public class GoogleAccount extends Account {
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
    public GoogleAccount(String accountName, String adrMail, AppUser owner, String accessToken, String tokenType,
            Long expiresInSeconds, String refreshToken, String scope) {
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
     * @param newAccessToken the accessToken to set
     */
    public void setAccessToken(final String newAccessToken) {
        this.accessToken = newAccessToken;
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
    public void setTokenType(final String newTokenType) {
        this.tokenType = newTokenType;
    }

    /**
     * @return the expiresInSeconds
     */
    public Long getExpiresInSeconds() {
        return expiresInSeconds;
    }

    /**
     * @param newExpiresInSeconds the expiresInSeconds to set
     */
    public void setExpiresInSeconds(final Long newExpiresInSeconds) {
        this.expiresInSeconds = newExpiresInSeconds;
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
    public void setRefreshToken(final String newRefreshToken) {
        this.refreshToken = newRefreshToken;
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
    public void setScope(final String newScope) {
        this.scope = scope;
    }

}
