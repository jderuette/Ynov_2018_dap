package com.ynov.dap.model.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class Folder.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Folder {

    /** The id. */
    private String id;

    /** The display name. */
    @JsonProperty("displayName")
    private String displayName;

    /** The child folder count. */
    @JsonProperty("childFolderCount")
    private Integer childFolderCount;

    /** The unread item count. */
    @JsonProperty("unreadItemCount")
    private Integer unreadItemCount;

    /** The total item count. */
    @JsonProperty("totalItemCount")
    private Integer totalItemCount;

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param val the new id
     */
    public void setId(final String val) {
        this.id = val;
    }

    /**
     * Gets the display name.
     *
     * @return the display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the display name.
     *
     * @param val the new display name
     */
    public void setDisplayName(final String val) {
        this.displayName = val;
    }

    /**
     * Gets the child folder count.
     *
     * @return the child folder count
     */
    public Integer getChildFolderCount() {
        return childFolderCount;
    }

    /**
     * Sets the child folder count.
     *
     * @param val the new child folder count
     */
    public void setChildFolderCount(final Integer val) {
        this.childFolderCount = val;
    }

    /**
     * Gets the unread item count.
     *
     * @return the unread item count
     */
    public Integer getUnreadItemCount() {
        return unreadItemCount;
    }

    /**
     * Sets the unread item count.
     *
     * @param val the new unread item count
     */
    public void setUnreadItemCount(final Integer val) {
        this.unreadItemCount = val;
    }

    /**
     * Gets the total item count.
     *
     * @return the total item count
     */
    public Integer getTotalItemCount() {
        return totalItemCount;
    }

    /**
     * Sets the total item count.
     *
     * @param val the new total item count
     */
    public void setTotalItemCount(final Integer val) {
        this.totalItemCount = val;
    }

}