package fr.ynov.dap.microsoft.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represent a folder from Outlook API.
 * @author Kévin Sibué
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutlookFolder {

    /**
     * Display name of current folder.
     */
    @JsonProperty("displayName")
    private String displayName;

    /**
     * Number of child folder for current folder.
     */
    @JsonProperty("childFolderCount")
    private Integer childFolderCount;

    /**
     * Number of unread item for current folder.
     */
    @JsonProperty("unreadItemCount")
    private Integer unreadItemCount;

    /**
     * Number of total item for current folder.
     */
    @JsonProperty("totalItemCount")
    private Integer totalItemCount;

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param val the displayName to set
     */
    public void setDisplayName(final String val) {
        this.displayName = val;
    }

    /**
     * @return the childFolderCount
     */
    public Integer getChildFolderCount() {
        return childFolderCount;
    }

    /**
     * @param val the childFolderCount to set
     */
    public void setChildFolderCount(final Integer val) {
        this.childFolderCount = val;
    }

    /**
     * @return the unreadItemCount
     */
    public Integer getUnreadItemCount() {
        return unreadItemCount;
    }

    /**
     * @param val the unreadItemCount to set
     */
    public void setUnreadItemCount(final Integer val) {
        this.unreadItemCount = val;
    }

    /**
     * @return the totalItemCount
     */
    public Integer getTotalItemCount() {
        return totalItemCount;
    }

    /**
     * @param val the totalItemCount to set
     */
    public void setTotalItemCount(final Integer val) {
        this.totalItemCount = val;
    }

}
