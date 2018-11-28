package fr.ynov.dap.model;

import fr.ynov.dap.model.enumeration.CredentialEnum;

/**
 * The Class Credential.
 */
public class Credential {

    /**
     * User's Id.
     */
    private String userId;

    /**
     * User's token.
     */
    private String token;

    /**
     * User's refresh token.
     */
    private String refreshToken;

    /**
     * Token's expiration time.
     */
    private long expirationTime;

    /**
     * Type a current credential. e.g. Microsoft, Google, ...
     */
    private CredentialEnum type;

    /**
     * Store tenant id for API which use it.
     */
    private String tenantId = "";

    /**
     * Gets the user id.
     *
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user id.
     *
     * @param val the userId to set
     */
    public void setUserId(final String val) {
        this.userId = val;
    }

    /**
     * Gets the token.
     *
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the token.
     *
     * @param val the token to set
     */
    public void setToken(final String val) {
        this.token = val;
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
     * Gets the expiration time.
     *
     * @return the expirationTime
     */
    public long getExpirationTime() {
        return expirationTime;
    }

    /**
     * Sets the expiration time.
     *
     * @param val the expirationTime to set
     */
    public void setExpirationTime(final long val) {
        this.expirationTime = val;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public CredentialEnum getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param val the type to set
     */
    public void setType(final CredentialEnum val) {
        this.type = val;
    }

    /**
     * Gets the tenant id.
     *
     * @return the tenantId
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Sets the tenant id.
     *
     * @param val the tenantId to set
     */
    public void setTenantId(final String val) {
        this.tenantId = val;
    }

}
