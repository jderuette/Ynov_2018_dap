package fr.ynov.dap.dap.data.microsoft.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class OutlookFolder.
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
	 * Gets the display name.
	 *
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Sets the display name.
	 *
	 * @param val
	 *            the displayName to set
	 */
	public void setDisplayName(final String val) {
		this.displayName = val;
	}

	/**
	 * Gets the child folder count.
	 *
	 * @return the childFolderCount
	 */
	public Integer getChildFolderCount() {
		return childFolderCount;
	}

	/**
	 * Sets the child folder count.
	 *
	 * @param val
	 *            the childFolderCount to set
	 */
	public void setChildFolderCount(final Integer val) {
		this.childFolderCount = val;
	}

	/**
	 * Gets the unread item count.
	 *
	 * @return the unreadItemCount
	 */
	public Integer getUnreadItemCount() {
		return unreadItemCount;
	}

	/**
	 * Sets the unread item count.
	 *
	 * @param val
	 *            the unreadItemCount to set
	 */
	public void setUnreadItemCount(final Integer val) {
		this.unreadItemCount = val;
	}

	/**
	 * Gets the total item count.
	 *
	 * @return the totalItemCount
	 */
	public Integer getTotalItemCount() {
		return totalItemCount;
	}

	/**
	 * Sets the total item count.
	 *
	 * @param val
	 *            the totalItemCount to set
	 */
	public void setTotalItemCount(final Integer val) {
		this.totalItemCount = val;
	}
}
