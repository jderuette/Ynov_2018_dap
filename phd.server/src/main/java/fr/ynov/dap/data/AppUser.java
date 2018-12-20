package fr.ynov.dap.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import fr.ynov.dap.microsoft.data.MicrosoftAccountData;

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

    /**.
     *
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<MicrosoftAccountData> microsoftAccounts;

    /**
     * @param account .
     */
  //TODO phd by Djer |POO| Il manque un "d" a ton "add"
    public void adGoogleAccount(final GoogleAccountData account) {
        account.setOwner(this);
        this.getGoogleAccounts().add(account);
    }

    /**
     * @param account Microsoft
     */
    //TODO phd by Djer |POO| Il manque un "d" a ton "add"
    public void adMicrosoftAccount(final MicrosoftAccountData account) {
        account.setOwner(this);
        this.getAccountsMicrosoft().add(account);
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
     * @return microsoftAccounts
     */
    public List<MicrosoftAccountData> getAccountsMicrosoft() {
        return microsoftAccounts;
    }

    /**
     * @param mGoogleAccounts the googleAccounts to set
     */
    public void setGoogleAccounts(final List<GoogleAccountData> mGoogleAccounts) {
        this.googleAccounts = mGoogleAccounts;
    }
}
