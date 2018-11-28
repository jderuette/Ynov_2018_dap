package fr.ynov.dap.microsoft.data;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mon_PC
 */
public class UnreadmailResult {
    /**.
     * propriété context
     */
    @JsonProperty("@odata.context")
    private String context;
    /**.
     * propriété odata.id
     */
    @JsonProperty("@odata.id")
    private String odataId;

    /**.
     * propriété id
     */
    @JsonProperty("id")
    private String id;

    /**.
     * propriété displayName
     */
    @JsonProperty("displayName")
    private String displayName;

    /**.
     * propriété parentFolderId
     */
    @JsonProperty("parentFolderId")
    private String parentFolderId;

    /**.
     * propriété childFolderCount
     */
    @JsonProperty("childFolderCount")
    private String childFolderCount;

    /**
     * @return context
     */
    public String getContext() {
        return context;
    }

    /**.
     * @param newContext new context
     */
    public void setContext(final String newContext) {
        this.context = newContext;
    }

    /**
     * @return oDataId
     */
    public String getOdataId() {
        return odataId;
    }

    /**.
     * @param newOdataId new odataId
     */
    public void setOdataId(final String newOdataId) {
        this.odataId = newOdataId;
    }

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param newId new id
     */
    public void setId(final String newId) {
        this.id = newId;
    }

    /**
     * @return displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**.
     * @param newDisplayName new displayname
     */
    public void setDisplayName(final String newDisplayName) {
        this.displayName = newDisplayName;
    }

    /**
     * @return parentfolderId
     */
    public String getParentFolderId() {
        return parentFolderId;
    }

    /**.
     * @param newParentFolderId new parent folder
     */
    public void setParentFolderId(final String newParentFolderId) {
        this.parentFolderId = newParentFolderId;
    }

    /**
     * @return childfoldercount
     */
    public String getChildFolderCount() {
        return childFolderCount;
    }

    /**.
     * @param newChildFolderCount new childFolder
     */
    public void setChildFolderCount(final String newChildFolderCount) {
        this.childFolderCount = newChildFolderCount;
    }

    /**
     * @return unreadItem
     */
    public int getUnreadItemCount() {
        return unreadItemCount;
    }

    /**.
     * @param newUnreadItemCount new unReadItem
     */
    public void setUnreadItemCount(final int newUnreadItemCount) {
        this.unreadItemCount = newUnreadItemCount;
    }

    /**
     * @return totalItem
     */
    public String getTotalItemCount() {
        return totalItemCount;
    }

    /**.
     * @param newTotalItemCount new totalItem
     */
    public void setTotalItemCount(final String newTotalItemCount) {
        this.totalItemCount = newTotalItemCount;
    }

    /**.
     * propriété unreadItemCount
     */
    @JsonProperty("unreadItemCount")
    private int unreadItemCount;

    /**.
     * propriété totalItemCount
     */
    @JsonProperty("totalItemCount")
    private String totalItemCount;
}
