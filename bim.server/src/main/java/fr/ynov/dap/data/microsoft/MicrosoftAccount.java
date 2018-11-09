package fr.ynov.dap.data.microsoft;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import fr.ynov.dap.data.AppUser;

@Entity
public class MicrosoftAccount {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @ManyToOne
    private AppUser owner;

    @OneToOne
    private TokenResponse token;

    /**
     * Tenant Id.
     */
    private String tenantId;

    public MicrosoftAccount() {

    }

    /**
     * @param name
     * @param token
     * @param tenantId
     */
    public MicrosoftAccount(String name, TokenResponse token, String tenantId) {
        super();
        this.name = name;
        this.token = token;
        this.tenantId = tenantId;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the owner
     */
    public AppUser getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(AppUser owner) {
        this.owner = owner;
    }

    /**
     * @return the token
     */
    public TokenResponse getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(TokenResponse token) {
        this.token = token;
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

}
