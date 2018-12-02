package fr.ynov.dap.data;

import fr.ynov.dap.model.enumeration.GuestStatusEventEnum;

// TODO: Auto-generated Javadoc
//TODO bal by Djer |JavaDoc| Je devrais me faire une moulinnette à correction "La note de {étudiant} est <<student note here>>", puis ne pas relire, ne pas completer la partie "note" et considérer la correction comme "consicencieusement terminé". Qu'en penses-tu ?
/**
 * The Class Guest.
 */
public class Guest {

	/** The mail. */
    private String mail;

	/** The status. */
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
	 * @param val the new mail
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
	 * @param val the new status
	 */
    public void setStatus(final GuestStatusEventEnum val) {
        this.status = val;
 }
}
