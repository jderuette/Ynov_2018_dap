
package fr.ynov.dap.microsoft;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Folder
{
  private String id;
  private String DisplayName;
  private String ParentFolderId;
  private Integer ChildFolderCount;
  private Integer UnreadItemCount;
  private Integer TotalItemCount;

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getDisplayName()
  {
    return DisplayName;
  }

  public void setDisplayName(String displayName)
  {
    DisplayName = displayName;
  }

  public String getParentFolderId()
  {
    return ParentFolderId;
  }

  public void setParentFolderId(String parentFolderId)
  {
    ParentFolderId = parentFolderId;
  }

  public Integer getChildFolderCount()
  {
    return ChildFolderCount;
  }

  public void setChildFolderCount(Integer childFolderCount)
  {
    ChildFolderCount = childFolderCount;
  }

  public Integer getUnreadItemCount()
  {
    return UnreadItemCount;
  }

  public void setUnreadItemCount(Integer unreadItemCount)
  {
    UnreadItemCount = unreadItemCount;
  }

  public Integer getTotalItemCount()
  {
    return TotalItemCount;
  }

  public void setTotalItemCount(Integer totalItemCount)
  {
    TotalItemCount = totalItemCount;
  }
}
