package fr.ynov.dap.data.google;

import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;

@Entity
public class GoogleAccount {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private AppUser owner;

    private String accountName;

    public final Integer getId() {
        return id;
    }

    public final void setId(Integer identifier) {
        this.id = identifier;
    }

    public final AppUser getOwner() {
        return owner;
    }

    public final void setOwner(AppUser appUser) {
        this.owner = appUser;
    }

    public final String getAccountName() {
        return accountName;
    }

    public final void setAccountName(String name) {
        this.accountName = name;
    }
}
