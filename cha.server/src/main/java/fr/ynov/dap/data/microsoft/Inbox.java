package fr.ynov.dap.data.microsoft;

import java.util.ArrayList;

import fr.ynov.dap.data.microsoft.MicrosoftAccount;
import fr.ynov.dap.model.outlook.Message;

/**
 * The Class Inbox.
 */
public class Inbox {
	/**
     * Store linked microsoft account.
     */
    private MicrosoftAccount account;

    /**
     * Store list messages.
     */
    private ArrayList<Message> messages;

    /**
     * Gets the account.
     *
     * @return the account
     */
    public MicrosoftAccount getAccount() {
        return account;
    }

    /**
     * Sets the account.
     *
     * @param val the account to set
     */
    public void setAccount(final MicrosoftAccount val) {
        this.account = val;
    }

    /**
     * Gets the messages.
     *
     * @return the messages
     */
    public ArrayList<Message> getMessages() {
        return messages;
    }

    /**
     * Sets the messages.
     *
     * @param val the messages to set
     */
    public void setMessages(final ArrayList<Message> val) {
        this.messages = val;
}
}
