package fr.ynov.dap.data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * ToDo.
 * @author Kévin Sibué
 *
 */
@Entity
public class MicrosoftAccount {

    /**
     * Unique.
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * Owner column.
     */
    @ManyToOne
    private AppUser owner;

    /**
     * Account name column.
     */
    @Column
    private String email;

    /**
     * User tenant id column.
     */
    @Column
    private String tenantId;

    /**
     * List of every google account for this user.
     */
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "account")
    @JoinColumn(name = "id")
    private TokenResponse token;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param val the id to set
     */
    public void setId(final Integer val) {
        this.id = val;
    }

    /**
     * @return the owner
     */
    public AppUser getOwner() {
        return owner;
    }

    /**
     * @param val the owner to set
     */
    public void setOwner(final AppUser val) {
        this.owner = val;
    }

    /**
     * @return the userKey
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param val the userKey to set
     */
    public void setEmail(final String val) {
        this.email = val;
    }

    /**
     * @return the token
     */
    public TokenResponse getToken() {
        return token;
    }

    /**
     * @param val the token to set
     */
    public void setToken(final TokenResponse val) {
        this.token = val;
    }

    /**
     * @return the tenant id
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * @param val the tenant id to set
     */
    public void setTenantId(final String val) {
        this.tenantId = val;
    }

}
