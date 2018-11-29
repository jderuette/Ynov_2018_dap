package fr.ynov.dap.data;

/**
 * Permet d'accéder à tous les champs nécéssaire au credential microsoft
 * @author abaracas
 *
 */
public class Credential {

    private String userId;
    private String token;
    private long expirationTime;
    private String tenantId = "";
    private String refreshToken;
    
    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }
    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }
    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }
    /**
     * @return the expirationTime
     */
    public long getExpirationTime() {
        return expirationTime;
    }
    /**
     * @param expirationTime the expirationTime to set
     */
    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
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
