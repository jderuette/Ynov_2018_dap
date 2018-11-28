package fr.ynov.dap.generalaccount.model;

/**
 *
 * @author Dom
 *
 */
public class GeneralEvent {

    /**
     *
     */
    private String subject;
    /**
     *
     */
    private String organisator;

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param mSubject the subject to set
     */
    public void setSubject(final String mSubject) {
        this.subject = mSubject;
    }

    /**
     * @return the organisator
     */
    public String getOrganisator() {
        return organisator;
    }

    /**
     * @param mOrganisator the organisator to set
     */
    public void setOrganisator(final String mOrganisator) {
        this.organisator = mOrganisator;
    }

    /**
     * @return the eventStart
     */
    public String getEventStart() {
        return eventStart;
    }

    /**
     * @param mEventStart the eventStart to set
     */
    public void setEventStart(final String mEventStart) {
        this.eventStart = mEventStart;
    }

    /**
     * @return the eventEnd
     */
    public String getEventEnd() {
        return eventEnd;
    }

    /**
     * @param mEventEnd the eventEnd to set
     */
    public void setEventEnd(final String mEventEnd) {
        this.eventEnd = mEventEnd;
    }

    /**
     *
     */
    private String eventStart;
    /**
     *
     */
    private String eventEnd;
}
