package fr.ynov.dap.dap.microsoft.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Dom .
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TotalMailNonLu {

    /**
    *
    */
    @JsonProperty("id")
    private String id;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param theId the id to set
     */
    public void setId(final String theId) {
        this.id = theId;
    }

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param theDisplayName the displayName to set
     */
    public void setDisplayName(final String theDisplayName) {
        this.displayName = theDisplayName;
    }

    /**
     * @return the parentFolderId
     */
    public String getParentFolderId() {
        return parentFolderId;
    }

    /**
     * @param theParentFolderId the parentFolderId to set
     */
    public void setParentFolderId(final String theParentFolderId) {
        this.parentFolderId = theParentFolderId;
    }

    /**
     * @return the childFolderCount
     */
    public int getChildFolderCount() {
        return childFolderCount;
    }

    /**
     * @param theChildFolderCount the childFolderCount to set
     */
    public void setChildFolderCount(final int theChildFolderCount) {
        this.childFolderCount = theChildFolderCount;
    }

    /**
     * @return the unreadItemCount
     */
    public int getUnreadItemCount() {
        return unreadItemCount;
    }

    /**
     * @param theUnreadItemCount the unreadItemCount to set
     */
    public void setUnreadItemCount(final int theUnreadItemCount) {
        this.unreadItemCount = theUnreadItemCount;
    }

    /**
     * @return the totalItemCount
     */
    public int getTotalItemCount() {
        return totalItemCount;
    }

    /**
     * @param theTotalItemCount the totalItemCount to set
     */
    public void setTotalItemCount(final int theTotalItemCount) {
        this.totalItemCount = theTotalItemCount;
    }

    /**
    *
    */
    @JsonProperty("displayName")
    private String displayName;

    /**
    *
    */
    @JsonProperty("parentFolderId")
    private String parentFolderId;

    /**
    *
    */
    @JsonProperty("childFolderCount")
    private int childFolderCount;

    /**
    *
    */
    @JsonProperty("unreadItemCount")
    private int unreadItemCount;

    /**
    *
    */
    @JsonProperty("totalItemCount")
    private int totalItemCount;

    /**
    *
    */
    @JsonProperty("@odata.context")
    private String context;

    /**
     * @return the context
     */
    public String getContext() {
        return context;
    }

    /**
     * @param mContext the context to set
     */
    public void setContext(final String mContext) {
        this.context = mContext;
    }

    /**
     *
     */
    @JsonProperty("@odata.id")
    private String oDataId;

    /**
     *
     * @return oDataId
     */
    public String getODataId() {
        return oDataId;
    }

    /**
     *
     * @param theODataId Modification de la valeur
     */
    public void setODataId(final String theODataId) {
        this.oDataId = theODataId;
    }

}
