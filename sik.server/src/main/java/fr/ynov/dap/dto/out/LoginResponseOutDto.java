package fr.ynov.dap.dto.out;

import fr.ynov.dap.model.enumeration.LoginStatusEnum;

/**
 * Class returned for API when user want to connect.
 * @author Kévin Sibué
 *
 */
public class LoginResponseOutDto {

    /**
     * Url to redirect user.
     */
    private String url;

    /**
     * Current connection status.
     */
    private int status;

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param val the url to set
     */
    public void setUrl(final String val) {
        this.url = val;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param val the status to set
     */
    public void setStatus(final int val) {
        this.status = val;
    }

    /**
     * @param val the status to set
     */
    public void setStatus(final LoginStatusEnum val) {
        this.status = val.getValue();
    }

}
