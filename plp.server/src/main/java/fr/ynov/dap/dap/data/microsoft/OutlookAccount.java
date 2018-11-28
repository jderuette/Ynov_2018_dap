package fr.ynov.dap.dap.data.microsoft;

import fr.ynov.dap.dap.data.AppUser;

import javax.persistence.*;

@Entity
public class OutlookAccount {
    @Id
    @GeneratedValue
    Integer id;

    @Column
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "token_id")
    private Token token;

    @ManyToOne
    AppUser owner;

    private String tenantId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
        this.token.setOwner(this);
    }

    public AppUser getOwner() {
        return owner;
    }

    public void setOwner(AppUser owner) {
        this.owner = owner;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

}
