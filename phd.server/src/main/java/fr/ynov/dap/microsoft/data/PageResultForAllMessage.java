package fr.ynov.dap.microsoft.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Dom .
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageResultForAllMessage {

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
     * @param mId the id to set
     */
    public void setId(final String mId) {
        this.id = mId;
    }

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param mDisplayName the displayName to set
     */
    public void setDisplayName(final String mDisplayName) {
        this.displayName = mDisplayName;
    }

    /**
     * @return the parentFolderId
     */
    public String getParentFolderId() {
        return parentFolderId;
    }

    /**
     * @param mParentFolderId the parentFolderId to set
     */
    public void setParentFolderId(final String mParentFolderId) {
        this.parentFolderId = mParentFolderId;
    }

    /**
     * @return the childFolderCount
     */
    public int getChildFolderCount() {
        return childFolderCount;
    }

    /**
     * @param mChildFolderCount the childFolderCount to set
     */
    public void setChildFolderCount(final int mChildFolderCount) {
        this.childFolderCount = mChildFolderCount;
    }

    /**
     * @return the unreadItemCount
     */
    public int getUnreadItemCount() {
        return unreadItemCount;
    }

    /**
     * @param mUnreadItemCount the unreadItemCount to set
     */
    public void setUnreadItemCount(final int mUnreadItemCount) {
        this.unreadItemCount = mUnreadItemCount;
    }

    /**
     * @return the totalItemCount
     */
    public int getTotalItemCount() {
        return totalItemCount;
    }

    /**
     * @param mTotalItemCount the totalItemCount to set
     */
    public void setTotalItemCount(final int mTotalItemCount) {
        this.totalItemCount = mTotalItemCount;
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
     * @return .
     */
    public String getODataId() {
        return oDataId;
    }

    /**
     *
     * @param mODataId .
     */
    public void setODataId(final String mODataId) {
        this.oDataId = mODataId;
    }

}
