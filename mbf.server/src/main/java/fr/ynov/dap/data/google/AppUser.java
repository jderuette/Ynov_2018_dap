package fr.ynov.dap.data.google;

import javax.persistence.*;
import java.util.List;

@Entity
public class AppUser {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="owner")
    private List<GoogleAccount> googleAccounts;

    public final void addGoogleAccount(GoogleAccount account){
        account.setOwner(this);
        this.getAccounts().add(account);
    }

    public final Integer getId() {
        return id;
    }

    public final void setId(Integer userId) {
        this.id = userId;
    }

    public final String getName() {
        return name;
    }

    public final void setName(String userName) {
        this.name = userName;
    }

    public final List<GoogleAccount> getAccounts() {
        return googleAccounts;
    }

    public final void setAccounts(List<GoogleAccount> accounts) {
        this.googleAccounts = accounts;
    }
}
