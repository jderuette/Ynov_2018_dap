package fr.ynov.dap.model;

import fr.ynov.dap.model.enumeration.AttendeeEventStatusEnum;

/**
 * Class that represent Attendee from every API.
 * @author Kévin Sibué
 *
 */
public class Attendee {

    /**
     * Store mail.
     */
    private String mail;

    /**
     * Store status.
     */
    private AttendeeEventStatusEnum status;

    /**
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * @param val the mail to set
     */
    public void setMail(final String val) {
        this.mail = val;
    }

    /**
     * @return the status
     */
    public AttendeeEventStatusEnum getStatus() {
        return status;
    }

    /**
     * @param val the status to set
     */
    public void setStatus(final AttendeeEventStatusEnum val) {
        this.status = val;
    }

}
