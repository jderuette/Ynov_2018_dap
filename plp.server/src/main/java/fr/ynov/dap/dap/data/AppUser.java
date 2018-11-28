package fr.ynov.dap.dap.data;

import fr.ynov.dap.dap.data.microsoft.OutlookAccount;

import javax.persistence.*;
import java.util.List;

@Entity
public class AppUser {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<GoogleAccount> googleAccount;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<OutlookAccount> outlookAccount;

    public void addGoogleAccount(GoogleAccount account){
        account.setOwner(this);
        this.googleAccount.add(account);
    }

    public List<GoogleAccount> getGoogleAccount() {
        return googleAccount;
    }

    public void addOutlookAccount(OutlookAccount account){
        account.setOwner(this);
        this.outlookAccount.add(account);
    }

    public List<OutlookAccount> getOutlookAccount() {
        return outlookAccount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
