package fr.ynov.dap.dap.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MailFolder {
	String displayName;
	String parentFolderid;
    Integer childFolderCount;
	Integer unreadItemCount;
	Integer totalItemCount;
	String id;
	
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getParentFolderid() {
		return parentFolderid;
	}
	public void setParentFolderid(String parentFolderid) {
		this.parentFolderid = parentFolderid;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
