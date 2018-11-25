package fr.ynov.dap.model.microsoft;

import java.util.ArrayList;

import fr.ynov.dap.microsoft.model.Message;

/**
 * Class that represent a list of message for a particular account.
 * @author Kévin Sibué
 *
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
     * @return the account
     */
    public MicrosoftAccount getAccount() {
        return account;
    }

    /**
     * @param val the account to set
     */
    public void setAccount(final MicrosoftAccount val) {
        this.account = val;
    }

    /**
     * @return the messages
     */
    public ArrayList<Message> getMessages() {
        return messages;
    }

    /**
     * @param val the messages to set
     */
    public void setMessages(final ArrayList<Message> val) {
        this.messages = val;
    }

}
