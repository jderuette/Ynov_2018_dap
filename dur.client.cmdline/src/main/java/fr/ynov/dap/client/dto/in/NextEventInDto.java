package fr.ynov.dap.client.dto.in;

import java.util.Date;

import com.google.gson.Gson;

/**
 * Class that represent next event for a user.
 * @author Kévin Sibué
 *
 */
public class NextEventInDto {

    /**
     * Store event summary.
     */
    private String summary;

    /**
     * Store starting date.
     */
    private Date startingDate;

    /**
     * Store ending date.
     */
    private Date endingDate;

    /**
     * Store status.
     */
    private Integer status;

    /**
     * Store user's status.
     */
    private Integer userStatus;

    /**
     * @return the eventSummary
     */
    public String getEventSummary() {
        return summary;
    }

    /**
     * @return the startingDate
     */
    public Date getStartingDate() {
        return startingDate;
    }

    /**
     * @return the endingDate
     */
    public Date getEndingDate() {
        return endingDate;
    }

    /**
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @return the userStatus
     */
    public Integer getUserStatus() {
        return userStatus;
    }

    /**
     * Transform json string to current class instance.
     * @param json String representation of current class
     * @return New NextEventInDto object
     */
    public static NextEventInDto fromJSON(final String json) {

        Gson gson = new Gson();

        return gson.fromJson(json, NextEventInDto.class);

    }

}
