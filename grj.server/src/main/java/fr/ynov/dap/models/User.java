package fr.ynov.dap.models;

import javax.persistence.*;
import java.util.List;

/**
 * User Model
 */
@Entity
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<GoogleAccount> googleAccountList;

    /**
     * Add a GoogleAccount to the user
     *
     * @param googleAccount GoogleAccount
     */
    public void addGoogleAccount(GoogleAccount googleAccount) {
        googleAccount.setOwner(this);
        this.getAccounts().add(googleAccount);
    }


    public List<GoogleAccount> getAccounts() {
        return this.googleAccountList;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
