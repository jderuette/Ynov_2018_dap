package fr.ynov.dap.data;

import fr.ynov.dap.model.enumeration.GuestStatusEventEnum;

/**
 * The Class Guest.
 */
public class Guest {
	 /**
     * Store mail.
     */
    private String mail;

    /**
     * Store status.
     */
    private GuestStatusEventEnum status;

    /**
     * Gets the mail.
     *
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * Sets the mail.
     *
     * @param val the mail to set
     */
    public void setMail(final String val) {
        this.mail = val;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    public GuestStatusEventEnum getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * @param val the status to set
     */
    public void setStatus(final GuestStatusEventEnum val) {
        this.status = val;
}
}
