package fr.ynov.dap.client.dto.in;

import com.google.gson.Gson;

/**
 * Represent number of unread mail for current logged user.
 * @author Kévin Sibué
 *
 */
public class UnreadMailInDto {

    /**
     * Store number of unread mail.
     */
    private Integer numberOfUnreadMail;

    /**
     * Default constructor.
     */
    public UnreadMailInDto() {

    }

    /**
     * @return the numberOfUnreadMail
     */
    public Integer getNumberOfUnreadMail() {
        return numberOfUnreadMail;
    }

    /**
     * @param val the numberOfUnreadMail to set.
     */
    public void setNumberOfUnreadMail(final Integer val) {
        this.numberOfUnreadMail = val;
    }

    /**
     * Transform json string to current class instance.
     * @param json String representation of current class
     * @return New UnreadMailInDto object
     */
    public static UnreadMailInDto fromJSON(final String json) {

        Gson gson = new Gson();

        return gson.fromJson(json, UnreadMailInDto.class);

    }

}
