package com.ynov.dap.model.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Folder {

	private String id;
	
    @JsonProperty("displayName")
    private String displayName;

    @JsonProperty("childFolderCount")
    private Integer childFolderCount;

    @JsonProperty("unreadItemCount")
    private Integer unreadItemCount;

    @JsonProperty("totalItemCount")
    private Integer totalItemCount;

	public String getId() {
		return id;
	}

	public void setId(final String val) {
		this.id = val;
	}
    
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(final String val) {
        this.displayName = val;
    }


    public Integer getChildFolderCount() {
        return childFolderCount;
    }

    public void setChildFolderCount(final Integer val) {
        this.childFolderCount = val;
    }

    public Integer getUnreadItemCount() {
        return unreadItemCount;
    }

    public void setUnreadItemCount(final Integer val) {
        this.unreadItemCount = val;
    }

    public Integer getTotalItemCount() {
        return totalItemCount;
    }
    
    public void setTotalItemCount(final Integer val) {
        this.totalItemCount = val;
    }


}