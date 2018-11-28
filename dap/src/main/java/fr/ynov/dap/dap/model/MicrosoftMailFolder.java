package fr.ynov.dap.dap.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author David_tepoche
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MicrosoftMailFolder {

    /**
     * Display name of current folder.
     */
    private String displayName;

    /**
     * Number of child folder for current folder.
     */
    private Integer childFolderCount;

    /**
     * Number of unread item for current folder.
     */
    private Integer unreadItemCount;

    /**
     * Number of total item for current folder.
     */
    private Integer totalItemCount;

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName the displayName to set
     */
    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    /**
     * @return the childFolderCount
     */
    public Integer getChildFolderCount() {
        return childFolderCount;
    }

    /**
     * @param childFolderCount the childFolderCount to set
     */
    public void setChildFolderCount(final Integer childFolderCount) {
        this.childFolderCount = childFolderCount;
    }

    /**
     * @return the unreadItemCount
     */
    public Integer getUnreadItemCount() {
        return unreadItemCount;
    }

    /**
     * @param unreadItemCount the unreadItemCount to set
     */
    public void setUnreadItemCount(final Integer unreadItemCount) {
        this.unreadItemCount = unreadItemCount;
    }

    /**
     * @return the totalItemCount
     */
    public Integer getTotalItemCount() {
        return totalItemCount;
    }

    /**
     * @param totalItemCount the totalItemCount to set
     */
    public void setTotalItemCount(final Integer totalItemCount) {
        this.totalItemCount = totalItemCount;
    }
}
