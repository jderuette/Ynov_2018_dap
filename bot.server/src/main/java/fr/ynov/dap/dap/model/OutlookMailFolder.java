package fr.ynov.dap.dap.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class OutlookMailFolder.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutlookMailFolder {
	
	/** The display name. */
	String displayName;
	
	/** The parent folderid. */
	String parentFolderid;
	
	/** The child folder count. */
	Integer childFolderCount;
	
	/** The unread item count. */
	Integer unreadItemCount;
	
	/** The total item count. */
	Integer totalItemCount;
	
	/** The id. */
	String id;

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
	 * @param displayName the new display name
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * Gets the parent folderid.
	 *
	 * @return the parent folderid
	 */
	public String getParentFolderid() {
		return parentFolderid;
	}

	/**
	 * Sets the parent folderid.
	 *
	 * @param parentFolderid the new parent folderid
	 */
	public void setParentFolderid(String parentFolderid) {
		this.parentFolderid = parentFolderid;
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
	 * @param childFolderCount the new child folder count
	 */
	public void setChildFolderCount(Integer childFolderCount) {
		this.childFolderCount = childFolderCount;
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
	 * @param unreadItemCount the new unread item count
	 */
	public void setUnreadItemCount(Integer unreadItemCount) {
		this.unreadItemCount = unreadItemCount;
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
	 * @param totalItemCount the new total item count
	 */
	public void setTotalItemCount(Integer totalItemCount) {
		this.totalItemCount = totalItemCount;
	}

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
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

}