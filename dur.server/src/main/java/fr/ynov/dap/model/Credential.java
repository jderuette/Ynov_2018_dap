package fr.ynov.dap.model;

/**
 * Represent user's credential data.
 * @author Kévin Sibué
 *
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
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param val the userId to set
     */
    public void setUserId(final String val) {
        this.userId = val;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param val the token to set
     */
    public void setToken(final String val) {
        this.token = val;
    }

    /**
     * @return the refreshToken
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * @param val the refreshToken to set
     */
    public void setRefreshToken(final String val) {
        this.refreshToken = val;
    }

    /**
     * @return the expirationTime
     */
    public long getExpirationTime() {
        return expirationTime;
    }

    /**
     * @param val the expirationTime to set
     */
    public void setExpirationTime(final long val) {
        this.expirationTime = val;
    }

}
