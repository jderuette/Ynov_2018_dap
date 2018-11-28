
package fr.ynov.dap.microsoft;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * Classe Folder
 * 
 * @author antod
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Folder
{
  /**
   * Variable id
   */
  private String id;
  /**
   * Variable DisplayName
   */
  private String DisplayName;
  /**
   * Variable ParentFolderId
   */
  private String ParentFolderId;
  /**
   * Variable ChileFolderCount
   */
  private Integer ChildFolderCount;
  /**
   * Variable UnreadItemCount
   */
  private Integer UnreadItemCount;
  /**
   * Variable TotalItemCount
   */
  private Integer TotalItemCount;

  /**
   * @return the id
   */
  public String getId()
  {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(String id)
  {
    this.id = id;
  }

  /**
   * @return the displayName
   */
  public String getDisplayName()
  {
    return DisplayName;
  }

  /**
   * @param displayName the displayName to set
   */
  public void setDisplayName(String displayName)
  {
    DisplayName = displayName;
  }

  /**
   * @return the parentFolderId
   */
  public String getParentFolderId()
  {
    return ParentFolderId;
  }

  /**
   * @param parentFolderId the parentFolderId to set
   */
  public void setParentFolderId(String parentFolderId)
  {
    ParentFolderId = parentFolderId;
  }

  /**
   * @return the childFolderCount
   */
  public Integer getChildFolderCount()
  {
    return ChildFolderCount;
  }

  /**
   * @param childFolderCount the childFolderCount to set
   */
  public void setChildFolderCount(Integer childFolderCount)
  {
    ChildFolderCount = childFolderCount;
  }

  /**
   * @return the unreadItemCount
   */
  public Integer getUnreadItemCount()
  {
    return UnreadItemCount;
  }

  /**
   * @param unreadItemCount the unreadItemCount to set
   */
  public void setUnreadItemCount(Integer unreadItemCount)
  {
    UnreadItemCount = unreadItemCount;
  }

  /**
   * @return the totalItemCount
   */
  public Integer getTotalItemCount()
  {
    return TotalItemCount;
  }

  /**
   * @param totalItemCount the totalItemCount to set
   */
  public void setTotalItemCount(Integer totalItemCount)
  {
    TotalItemCount = totalItemCount;
  }

}
