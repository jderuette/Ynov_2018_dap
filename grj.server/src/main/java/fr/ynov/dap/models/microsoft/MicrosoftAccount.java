package fr.ynov.dap.models.microsoft;

import fr.ynov.dap.models.common.User;

import javax.persistence.*;
import java.util.Date;

/**
 * Microsoft Account
 */
@Entity
public class MicrosoftAccount {

    private final static int TEXT_FIELD_MAX_LENGTH = 5000;

    /**
     * MicrosoftAccount's ID.
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * MicrosoftAccount's name.
     */
    @Column
    private String name;

    /**
     * MicrosoftAccount's tenant id.
     */
    @Column(length = TEXT_FIELD_MAX_LENGTH)
    private String tenantId;

    @Column
    private String email;

    @Column
    private Date tokenExpirationTime;

    @Column(length = TEXT_FIELD_MAX_LENGTH)
    private String token;

    @Column(length = TEXT_FIELD_MAX_LENGTH)
    private String refreshToken;


    /**
     * MicrosoftAccount's owner.
     */
    @ManyToOne
    private User owner;


    /**
     * Get MicrosoftAccount's ID.
     *
     * @return MicrosoftAccount's ID.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Get MicrosoftAccount's name.
     *
     * @return MicrosoftAccount's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set MicrosoftAccount's name.
     *
     * @param name MicrosoftAccount's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get MicrosoftAccount's owner.
     *
     * @return MicrosoftAccount's owner.
     */
    public User getOwner() {
        return owner;
    }

    /**
     * Set MicrosoftAccount's owner.
     *
     * @param owner MicrosoftAccount's owner.
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getTokenExpirationTime() {
        return tokenExpirationTime;
    }

    public void setTokenExpirationTime(Date tokenExpirationTime) {
        this.tokenExpirationTime = tokenExpirationTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {

        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
