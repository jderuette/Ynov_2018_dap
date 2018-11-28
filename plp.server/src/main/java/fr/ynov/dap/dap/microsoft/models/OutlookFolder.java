package fr.ynov.dap.dap.microsoft.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OutlookFolder {
    @JsonProperty("displayName")
    private String displayName;

    @JsonProperty("childFolderCount")
    private Integer childFolderCount;

    @JsonProperty("unreadItemCount")
    private Integer unreadItemCount;

    @JsonProperty("totalItemCount")
    private Integer totalItemCount;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Integer getChildFolderCount() {
        return childFolderCount;
    }

    public void setChildFolderCount(Integer childFolderCount) {
        this.childFolderCount = childFolderCount;
    }

    public Integer getUnreadItemCount() {
        return unreadItemCount;
    }

    public void setUnreadItemCount(Integer unreadItemCount) {
        this.unreadItemCount = unreadItemCount;
    }

    public Integer getTotalItemCount() {
        return totalItemCount;
    }

    public void setTotalItemCount(Integer totalItemCount) {
        this.totalItemCount = totalItemCount;
    }
}
