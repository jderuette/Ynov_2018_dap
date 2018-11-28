package fr.ynov.dap.client.dto.in;

import com.google.gson.Gson;

/**
 * Represent a server information that contain only the number of contact for the logged user.
 * @author Kévin Sibué
 *
 */
public class NumberContactInDto {

    /**
     * Store number of contacts.
     */
    private Integer numberOfContacts;

    /**
     * @return the numberOfContacts
     */
    public Integer getNumberOfContacts() {
        return numberOfContacts;
    }

    /**
     * @param val the numberOfContacts to set
     */
    public void setNumberOfContacts(final Integer val) {
        this.numberOfContacts = val;
    }

    /**
     * Transform json string to current class instance.
     * @param json String representation of current class
     * @return New NumberContactInDto object
     */
    public static NumberContactInDto fromJSON(final String json) {

        Gson gson = new Gson();

        return gson.fromJson(json, NumberContactInDto.class);

    }

}
