package dap.client.model.dto;

/**
 *
 * @author David_tepoche
 *
 */
public class GoogleAccountResponse {
    /**
     * the id.
     */
    private Integer id;

    /**
     * the accountName.
     */
    private String accountName;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param googleAccountId the id to set
     */
    public void setId(final Integer googleAccountId) {
        this.id = googleAccountId;
    }

    /**
     * @return the accountName
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * @param acctName the accountName to set
     */
    public void setAccountName(final String acctName) {
        this.accountName = acctName;
    }

}
