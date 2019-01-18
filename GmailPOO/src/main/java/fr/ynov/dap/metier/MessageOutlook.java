package fr.ynov.dap.metier;

import fr.ynov.dap.outlookService.Message;

public class MessageOutlook {
    private String AccountName;
    private Message[] messages;

    /**
     * @param accountName
     * @param messages
     */
    public MessageOutlook(final String accountName, final Message[] messages) {
        super();
        AccountName = accountName;
        this.messages = messages;
    }

    /**
     * 
     */
    public MessageOutlook() {
        super();

    }

    /**
     * @return the accountName
     */
    public String getAccountName() {
        return AccountName;
    }

    /**
     * @param newAccountName the accountName to set
     */
    public void setAccountName(final String newAccountName) {
        AccountName = newAccountName;
    }

    /**
     * @return the messages
     */
    public Message[] getMessages() {
        return messages;
    }

    /**
     * @param newMessages the messages to set
     */
    public void setMessages(final Message[] newMessages) {
        this.messages = newMessages;
    }

}
