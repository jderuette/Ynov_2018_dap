package com.ynov.dap.model;


/**
 * The Class MailModel.
 */
public class MailModel {


    /** The un read. */
    private Integer unRead;

    /**
     * Gets the un read.
     *
     * @return the un read
     */
    public Integer getUnRead() {
       return unRead;
    }

    /**
     * Sets the un read.
     *
     * @param inUnRead the new un read
     */
    public void setUnRead(final Integer inUnRead) {
       this.unRead = inUnRead;
    }

    /**
     * Instantiates a new mail model.
     *
     * @param inUnread the in unread
     */
    public MailModel(final Integer inUnread) {
       this.setUnRead(inUnread);
    }

	/**
	 * Instantiates a new mail model.
	 */
	public MailModel() {
	}


}
