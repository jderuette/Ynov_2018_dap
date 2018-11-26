package fr.ynov.dap.models;

import javax.persistence.*;
import java.util.List;

/**
 * User Model
 */
@Entity
public class User {

    /**
     * User ID
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * User Name
     */
    @Column
    private String name;

    /**
     * User GoogleAccounts
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<GoogleAccount> googleAccountList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<MicrosoftAccount> microsoftAccountList;

    /**
     * Add a GoogleAccount to the user
     *
     * @param googleAccount GoogleAccount
     */
    public void addGoogleAccount(GoogleAccount googleAccount) {
        googleAccount.setOwner(this);
        this.getGoogleAccountList().add(googleAccount);
    }

    public void addMicrosoftAccount(MicrosoftAccount microsoftAccount) {
        microsoftAccount.setOwner(this);
        this.getMicrosoftAccountList().add(microsoftAccount);
    }

    /**
     * User GoogleAccount getter
     *
     * @return list of GoogleAccount
     */
    public List<GoogleAccount> getGoogleAccountList() {
        return this.googleAccountList;
    }

    public List<MicrosoftAccount> getMicrosoftAccountList() {
        return microsoftAccountList;
    }

    /**
     * User ID getter
     *
     * @return ID
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * User Name getter
     *
     * @return Name
     */
    public String getName() {
        return this.name;
    }

    /**
     * User Name setter
     *
     * @param name Name
     */
    public void setName(String name) {
        this.name = name;
    }


}
