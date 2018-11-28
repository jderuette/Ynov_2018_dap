package fr.ynov.dap.dap.data;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 *
 * @author David_tepoche
 *
 */
@Entity
public class MicrosoftAccount {

    /**
     * primaryKey of microsoftAccount.
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * the alias for the microsoftAccount.
     */
    @Column
    private String accountName;

    /**
     * the tenant id , needed to use the api microsoft.
     */
    @Column
    private String tenantId;

    /**
     * foreign Key from AppUSer.
     *
     * @JsonBackReference is to explain at jackson mapper to avoid the infinity loop
     */
    @ManyToOne
    @JsonBackReference
    private AppUser owner;

    /**
     * the token of the client.
     */
    @Column(columnDefinition = "TEXT")
    private String accessToken;

    /**
     * Token type .
     */
    @Column
    private String tokenType;

    /**
     * Lifetime in seconds of the access token (for example 3600 for an hour).
     */
    @Column
    private Date expirationDate;

    /**
     * Refresh token which can be used to obtain new access tokens.
     *
     */
    @Column(columnDefinition = "TEXT")
    private String refreshToken;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param microsoftAccountId the id to set
     */
    public void setId(final Integer microsoftAccountId) {
        this.id = microsoftAccountId;
    }

    /**
     * @return the accountName
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * @param acntName the accountName to set
     */
    public void setAccountName(final String acntName) {
        this.accountName = acntName;
    }

    /**
     * @return the tenantId
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * @param tentId the tenantId to set
     */
    public void setTenantId(final String tentId) {
        this.tenantId = tentId;
    }

    /**
     * @return the owner
     */
    public AppUser getOwner() {
        return owner;
    }

    /**
     * @param ownr the owner to set
     */
    public void setOwner(final AppUser ownr) {
        this.owner = ownr;
    }

    /**
     * @return the accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * @param accessTkn the accessToken to set
     */
    public void setAccessToken(final String accessTkn) {
        this.accessToken = accessTkn;
    }

    /**
     * @return the tokenType
     */
    public String getTokenType() {
        return tokenType;
    }

    /**
     * @param tknType the tokenType to set
     */
    public void setTokenType(final String tknType) {
        this.tokenType = tknType;
    }

    /**
     * @return the expirationDate
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    /**
     * @param expiDate the expirationDate to set
     */
    public void setExpirationDate(final Date expiDate) {
        this.expirationDate = expiDate;
    }

    /**
     * @param expiInSeconde the expirationDate to set
     */
    public void setExpirationDate(final int expiInSeconde) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.SECOND, expiInSeconde);
        this.expirationDate = now.getTime();
    }

    /**
     * @return the refreshToken
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * @param rfshToken the refreshToken to set
     */
    public void setRefreshToken(final String rfshToken) {
        this.refreshToken = rfshToken;
    }
}
