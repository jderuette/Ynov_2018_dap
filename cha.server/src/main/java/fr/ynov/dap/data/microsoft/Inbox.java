package fr.ynov.dap.data.microsoft;

import java.util.ArrayList;

import fr.ynov.dap.data.microsoft.MicrosoftAccount;
import fr.ynov.dap.model.outlook.Message;

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
