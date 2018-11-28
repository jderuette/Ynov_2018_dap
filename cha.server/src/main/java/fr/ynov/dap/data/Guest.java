package fr.ynov.dap.data;

import fr.ynov.dap.model.enumeration.GuestStatusEventEnum;

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
    public GuestStatusEventEnum getStatus() {
        return status;
    }

    /**
     * @param val the status to set
     */
    public void setStatus(final GuestStatusEventEnum val) {
        this.status = val;
}
}
