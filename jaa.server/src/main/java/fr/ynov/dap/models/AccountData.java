package fr.ynov.dap.models;

/**
 * Accout data that will be displayed on the Admin Controller.
 */
public class AccountData {
    /**
     * Account type enum.
     */
    public enum Type {
        /**
         * Google account.
         */
        Google,
        /**
         * Microsoft account.
         */
        Microsoft
    }

    /**
     * User key of the AppUser account.
     */
    private String userKey;

    /**
     * @return the userKey
     */
    public String getUserKey() {
        return userKey;
    }

    /**
     * @param key the userKey to set
     */
    public void setUserKey(final String key) {
        this.userKey = key;
    }

    /**
     * @return the accountName
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * @param name the accountName to set
     */
    public void setAccountName(final String name) {
        this.accountName = name;
    }

    /**
     * @return the accountType
     */
    public Type getAccountType() {
        return accountType;
    }

    /**
     * @param type the accountType to set
     */
    public void setAccountType(final Type type) {
        this.accountType = type;
    }

    /**
     * @return the tenantId
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * @param tenant the tenantId to set
     */
    public void setTenantId(final String tenant) {
        this.tenantId = tenant;
    }

    /**
     * @return the accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * @param access the accessToken to set
     */
    public void setAccessToken(final String access) {
        this.accessToken = access;
    }

    /**
     * @return the refreshToken
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * @param refresh the refreshToken to set
     */
    public void setRefreshToken(final String refresh) {
        this.refreshToken = refresh;
    }

    /**
     * @return the expirationTimeMilliseconds
     */
    public long getExpirationTimeMilliseconds() {
        return expirationTimeMilliseconds;
    }

    /**
     * @param expiration the expirationTimeMilliseconds to set
     */
    public void setExpirationTimeMilliseconds(final long expiration) {
        this.expirationTimeMilliseconds = expiration;
    }

    /**
     * Account name.
     */
    private String accountName;

    /**
     * Account type. At first, I was thinking of using an enum
     */
    private Type accountType;

    /**
     * TenandId, only for Microsoft accounts.
     */
    private String tenantId;

    /**
     * Access token.
     */
    private String accessToken;

    /**
     * Refresh token.
     */
    private String refreshToken;

    /**
     * Expiration time in milliseconds.
     */
    private long expirationTimeMilliseconds;

}
