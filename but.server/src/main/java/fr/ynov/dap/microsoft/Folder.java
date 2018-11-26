package fr.ynov.dap.microsoft;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Folder {
    private String id;
    private String displayName;
    private String parentFolderId;
    private int childFolderCount;
    private int unreadItemCount;
    private int totalItemCount;
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * @param idFolder the id to set
     */
    public void setId(final String idFolder) {
        this.id = idFolder;
    }
    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }
    /**
     * @param name the displayName to set
     */
    public void setDisplayName(final String name) {
        this.displayName = name;
    }
    /**
     * @return the parentFolderId
     */
    public String getParentFolderId() {
        return parentFolderId;
    }
    /**
     * @param parentId the parentFolderId to set
     */
    public void setParentFolderId(final String parentId) {
        this.parentFolderId = parentId;
    }
    /**
     * @return the childFolderCount
     */
    public int getChildFolderCount() {
        return childFolderCount;
    }
    /**
     * @param childFolder the childFolderCount to set
     */
    public void setChildFolderCount(final int childFolder) {
        this.childFolderCount = childFolder;
    }
    /**
     * @return the unreadItemCount
     */
    public int getUnreadItemCount() {
        return unreadItemCount;
    }
    /**
     * @param unreadItem the unreadItemCount to set
     */
    public void setUnreadItemCount(final int unreadItem) {
        this.unreadItemCount = unreadItem;
    }
    /**
     * @return the totalItemCount
     */
    public int getTotalItemCount() {
        return totalItemCount;
    }
    /**
     * @param totalItem the totalItemCount to set
     */
    public void setTotalItemCount(final int totalItem) {
        this.totalItemCount = totalItem;
    }
}
