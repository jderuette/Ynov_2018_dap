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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AppUser getOwner() {
        return owner;
    }

    public void setOwner(AppUser owner) {
        this.owner = owner;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String name) {
        this.accountName = accountName;
    }
}
