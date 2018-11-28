package fr.ynov.dap.microsoft.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author Dom .
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {
    /**
     *
     */
    private String id;
    /**
     *
     */
    private String subject;
    /**
     *
     */
    private Recipient organizer;
    /**
     *
     */
    private DateTimeTimeZone start;
    /**
     *
     */
    private DateTimeTimeZone end;

    /**
     *
     * @return .
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param mId .
     */
    public void setId(final String mId) {
        this.id = mId;
    }

    /**
     *
     * @return .
     */
    public String getSubject() {
        return subject;
    }

    /**
     *
     * @param mSubject .
     */
    public void setSubject(final String mSubject) {
        this.subject = mSubject;
    }

    /**
     *
     * @return .
     */
    public Recipient getOrganizer() {
        return organizer;
    }

    /**
     *
     * @param mOrganizer .
     */
    public void setOrganizer(final Recipient mOrganizer) {
        this.organizer = mOrganizer;
    }

    /**
     *
     * @return .
     */
    public DateTimeTimeZone getStart() {
        return start;
    }

    /**
     *
     * @param mStart .
     */
    public void setStart(final DateTimeTimeZone mStart) {
        this.start = mStart;
    }

    /**
     *
     * @return .
     */
    public DateTimeTimeZone getEnd() {
        return end;
    }

    /**
     *
     * @param mEnd .
     */
    public void setEnd(final DateTimeTimeZone mEnd) {
        this.end = mEnd;
    }
}
