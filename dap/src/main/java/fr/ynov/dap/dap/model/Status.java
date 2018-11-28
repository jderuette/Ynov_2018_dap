package fr.ynov.dap.dap.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author David_tepoche
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Status {
    /**
     * get the status.
     */
    private String response;

    /**
     * @return the response
     */
    public String getResponse() {
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(final String response) {
        this.response = response;
    }
}
