/**
 * 
 */
package fr.ynov.dap.metier;

import com.google.api.client.auth.oauth2.Credential;

/**
 * @author acer
 *
 */
//TODO bes by Djer |POO| Une petite Javadoc serait utile. Ce code ne semble pas (plus) utilisé. A supprimer ? 
public class GoogleDataStore {
    private String accountName;
    private Credential credential;
    private String accessToken;
    private String refreshToken;
    private Long expirationTimeMilliseconds;

    /**
     * 
     */
    public GoogleDataStore() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @param accountName
     * @param credential
     */
    public GoogleDataStore(final String accountName, final Credential credential) {
        super();
        this.accountName = accountName;
        this.credential = credential;
        this.accessToken = credential.getAccessToken();
        this.refreshToken = credential.getRefreshToken();
        this.expirationTimeMilliseconds = credential.getExpirationTimeMilliseconds();
    }

    /**
     * @return the accountName
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * @param newAccountName the accountName to set
     */
    public void setAccountName(final String newAccountName) {
        this.accountName = newAccountName;
    }

    /**
     * @return the credential
     */
    public Credential getCredential() {
        return credential;
    }

    /**
     * @param newCredential the credential to set
     */
    public void setCredential(final Credential newCredential) {
        this.credential = newCredential;
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
     * @return the expirationTimeMilliseconds
     */
    public Long getExpirationTimeMilliseconds() {
        return expirationTimeMilliseconds;
    }

    /**
     * @param newExpirationTimeMilliseconds the expirationTimeMilliseconds to set
     */
    public void setExpirationTimeMilliseconds(final Long newExpirationTimeMilliseconds) {
        this.expirationTimeMilliseconds = newExpirationTimeMilliseconds;
    }

}
