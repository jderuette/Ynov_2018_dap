package fr.ynov.dap.data.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * outlook folder item
 * @author MBILLEMAZ
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Folder {
    /**
     * id.
     */
    private String id;

    /**
     * name.
     */
    private String displayName;

    /**
     * number of unread mails.
     */
    private Integer unreadItemCount;

    /**
     * total mails.
     */
    private Integer totalItemCount;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * @return the unreadItemCount
     */
    public Integer getUnreadItemCount() {
        return unreadItemCount;
    }

    /**
     * @param unreadItemCount the unreadItemCount to set
     */
    public void setUnreadItemCount(Integer unreadItemCount) {
        this.unreadItemCount = unreadItemCount;
    }

    /**
     * @return the totalItemCount
     */
    public Integer getTotalItemCount() {
        return totalItemCount;
    }

    /**
     * @param totalItemCount the totalItemCount to set
     */
    public void setTotalItemCount(Integer totalItemCount) {
        this.totalItemCount = totalItemCount;
    }

}
