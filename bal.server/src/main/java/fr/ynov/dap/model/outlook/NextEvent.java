package fr.ynov.dap.model.outlook;

import java.util.Date;

import fr.ynov.dap.model.EventAllApi;

public class NextEvent {

    private String summary;

    private Date startDate;

    private Date endDate;

    private Integer userStatus;

    public NextEvent(final EventAllApi evnt) {
        this.setSummary(evnt.getSubject());
        this.setStartDate(evnt.getStart());
        this.setEndDate(evnt.getEnd());
        this.setUserStatus(evnt.getCurrentUserStatus().getValue());
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(final String val) {
        this.summary = val;
    }

    public Date getStartingDate() {
        return startDate;
    }

    public void setStartDate(final Date val) {
        this.startDate = val;
    }

    public Date getEndingDate() {
        return endDate;
    }

    public void setEndDate(final Date val) {
        this.endDate = val;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(final Integer val) {
        this.userStatus = val;
}
}
