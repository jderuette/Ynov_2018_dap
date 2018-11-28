package fr.ynov.dap.models.common;

import java.util.Date;

/**
 * Event
 */
public class Event {

    /**
     * name
     */
    private String name;

    /**
     * startDate
     */
    private Date startDate;

    /**
     * endDate
     */
    private Date endDate;

    /*
    GETTERS AND SETTERS
     */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
