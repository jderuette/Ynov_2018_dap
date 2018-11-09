package fr.ynov.dap.client.model;

/**
 * .
 * @author Dom
 *
 */
public class Event {
    /**.
     * eventBegin is a String variable containing the date of the event begin
     * eventFinish  is a String variable containing the date of the event finish
     * eventStatus is a String variable containing the information of the event status
     * eventUncomming is a String variable containing the name of the event
     */

    /**.
     * eventBegin is a String variable containing the date of the event begin
     */
    private String eventBegin;
    /** .
     * eventFinish  is a String variable containing the date of the event finish
     */
    private String eventFinish;
    /**.
     * eventStatus is a String variable containing the information of the event status
     */
    private String eventStatus;
    /**.
     * eventUncomming is a String variable containing the name of the event
     */
    private String eventUncomming;

    /**
     * @return the eventBegin
     */
    public String getEventBegin() {
        return eventBegin;
    }

    /**
     * @param mEventBegin the eventBegin to set
     */
    public void setEventBegin(final String mEventBegin) {
        this.eventBegin = mEventBegin;
    }

    /**
     * @return the eventFinish
     */
    public String getEventFinish() {
        return eventFinish;
    }

    /**
     * @param mEventFinish the eventFinish to set
     */
    public void setEventFinish(final String mEventFinish) {
        this.eventFinish = mEventFinish;
    }

    /**
     * @return the eventStatus
     */
    public String getEventStatus() {
        return eventStatus;
    }

    /**
     * @param mEventStatus the eventStatus to set
     */
    public void setEventStatus(final String mEventStatus) {
        this.eventStatus = mEventStatus;
    }

    /**
     * @return the eventUncomming
     */
    public String getEventUncomming() {
        return eventUncomming;
    }

    /**
     * @param mEventUncomming the eventUncomming to set
     */
    public void setEventUncomming(final String mEventUncomming) {
        this.eventUncomming = mEventUncomming;
    }

}
