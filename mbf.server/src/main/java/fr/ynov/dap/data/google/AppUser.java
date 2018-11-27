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

    public void addGoogleAccount(GoogleAccount account){
        account.setOwner(this);
        this.getAccounts().add(account);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer userId) {
        this.id = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String userName) {
        this.name = userName;
    }

    public List<GoogleAccount> getAccounts() {
        return googleAccounts;
    }

    public void setAccounts(List<GoogleAccount> accounts) {
        this.googleAccounts = accounts;
    }
}
