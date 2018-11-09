package fr.ynov.dap.client.model;

/**.
 * Email model
 * @author Dom
 *
 */
public class Email {

    /**
     * @param nbMessageUnread .
     */
    private String nbMessageUnread;

    /**
     * @return the nbMessageUnread
     */
    public String getNbMessageUnread() {
        return nbMessageUnread;
    }

    /**
     * @param mNbMessageUnread the nbMessageUnread to set
     */
    public void setNbMessageUnread(final String mNbMessageUnread) {
        this.nbMessageUnread = mNbMessageUnread;
    }
}
