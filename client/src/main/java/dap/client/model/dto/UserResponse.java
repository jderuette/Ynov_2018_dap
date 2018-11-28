package dap.client.model.dto;

import java.util.List;

/**
 *
 * @author David_tepoche
 *
 */
public class UserResponse {

    /**
     * id.
     */
    private Integer id;
    /**
     * user in BDD.
     */
    private String userKey;

    /**
     * all the account related of the userKey.
     */
    private List<GoogleAccountResponse> accounts;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param userId the id to set
     */
    public void setId(final Integer userId) {
        this.id = userId;
    }

    /**
     * @return the userKey
     */
    public String getUserKey() {
        return userKey;
    }

    /**
     * @param usrKey the userKey to set
     */
    public void setUserKey(final String usrKey) {
        this.userKey = usrKey;
    }

    /**
     * @return the accounts
     */
    public List<GoogleAccountResponse> getAccounts() {
        return accounts;
    }

    /**
     * @param accts the accounts to set
     */
    public void setAccounts(final List<GoogleAccountResponse> accts) {
        this.accounts = accts;
    }

}
