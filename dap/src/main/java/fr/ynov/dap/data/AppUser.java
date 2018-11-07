package fr.ynov.dap.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Dom
 *
 */
@Entity
public class AppUser {
    /**
     * .
     */
    @Id
    @GeneratedValue
    private int id;

    /**
     * .
     */
    @Column(unique = true)
    private String name;

    /**
     * .
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<GoogleAccountData> googleAccounts;

    /**
     * @param account .
     */
    public void adGoogleAccount(final GoogleAccountData account) {
        account.setOwner(this);
        this.getGoogleAccounts().add(account);
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param mId the id to set
     */
    public void setId(final int mId) {
        this.id = mId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param mName the name to set
     */
    public void setName(final String mName) {
        this.name = mName;
    }

    /**
     * @return the googleAccounts
     */
    public List<GoogleAccountData> getGoogleAccounts() {
        return googleAccounts;
    }

    /**
     * @param mGoogleAccounts the googleAccounts to set
     */
    public void setGoogleAccounts(final List<GoogleAccountData> mGoogleAccounts) {
        this.googleAccounts = mGoogleAccounts;
    }
}
