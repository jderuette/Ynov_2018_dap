package fr.ynov.dap.client.dto.in;

import com.google.gson.Gson;

/**
 * Class returned for API when user try to connect.
 * @author Kévin Sibué
 *
 */
public class LoginResponseInDto {

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
     * Transform json string to current class instance.
     * @param json String representation of current class
     * @return New LoginResponseInDto object
     */
    public static LoginResponseInDto fromJSON(final String json) {

        Gson gson = new Gson();

        return gson.fromJson(json, LoginResponseInDto.class);

    }

}
