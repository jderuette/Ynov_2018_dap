package fr.ynov.dap.data.google;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.google.api.client.auth.oauth2.StoredCredential;

import fr.ynov.dap.data.AppUser;

/**
 * Model for GoogleAccount.
 * @author thibault
 *
 */
@Entity
public class GoogleAccount {
    /**
     * Id of entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Google Account Name.
     */
    private String accountName;

    /**
     * Owner of google account.
     */
    @ManyToOne
    private AppUser owner;

    /**
     * Access token.
     */
    private String accessToken;

    /**
     * Expiration of access token.
     */
    private Long expirationTimeMilliseconds;

    /**
     * Refresh token.
     */
    private String refreshToken;

    /**
     * Default constructor of GoogleAccount.
     */
    public GoogleAccount() {
    }

    /**
     * Constructor of GoogleAccount with credential.
     * @param user Owner of this account
     * @param key account name
     * @param credential google credential
     */
    public GoogleAccount(final String key, final AppUser user, final StoredCredential credential) {
        this.accountName = key;
        this.owner = user;
        this.accessToken = credential.getAccessToken();
        this.expirationTimeMilliseconds = credential.getExpirationTimeMilliseconds();
        this.refreshToken = credential.getRefreshToken();
    }

    /**
     * Apply a credential to this entity.
     * @param credential google credential
     */
    public void apply(final StoredCredential credential) {
        this.accessToken = credential.getAccessToken();
        this.expirationTimeMilliseconds = credential.getExpirationTimeMilliseconds();
        this.refreshToken = credential.getRefreshToken();
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
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
     * @return the owner
     */
    public AppUser getOwner() {
        return owner;
    }

    /**
     * @param ownerUser the owner to set
     */
    public void setOwner(final AppUser ownerUser) {
        this.owner = ownerUser;
    }

    /**
     * @return the accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * @param token the accessToken to set
     */
    public void setAccessToken(final String token) {
        this.accessToken = token;
    }

    /**
     * @return the expirationTimeMilliseconds
     */
    public Long getExpirationTimeMilliseconds() {
        return expirationTimeMilliseconds;
    }

    /**
     * @param expirationTimeMs the expirationTimeMilliseconds to set
     */
    public void setExpirationTimeMilliseconds(final Long expirationTimeMs) {
        this.expirationTimeMilliseconds = expirationTimeMs;
    }

    /**
     * @return the refreshToken
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * @param token the refreshToken to set
     */
    public void setRefreshToken(final String token) {
        this.refreshToken = token;
    }
}
