package fr.ynov.dap.microsoft;

import fr.ynov.dap.microsoft.entity.Contact;
import fr.ynov.dap.microsoft.entity.Event;
import fr.ynov.dap.microsoft.entity.Message;
import fr.ynov.dap.microsoft.entity.OutlookFolder;
import fr.ynov.dap.microsoft.entity.OutlookUser;
import fr.ynov.dap.microsoft.entity.PagedResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Service to manage outlook API.
 * @author Robin DUDEK
 *
 */
public interface OutlookApiCalls {
    @GET("/v1.0/me")
    Call<OutlookUser> getCurrentUser();

    /**
     * Get details for a specific folder.
     * @param folderName Folder name
     * @return Outlook folder.
     */
    @GET("/v1.0/me/mailFolders/{folderName}")
    Call<OutlookFolder> getFolder(@Path("folderName") String folderName);

    /**
     * Get details for a specific folder.
     * @param folderName Folder name
     * @return Outlook folder.
     */
    @GET("/v1.0/me/mailfolders/{folderid}/messages")
    Call<PagedResult<Message>> getMessages(
        @Path("folderid") String folderId, 
        @Query("$orderby") String orderBy,
        @Query("$select") String select, 
        @Query("$top") Integer maxResults
    );
  
    @GET("/v1.0/me/events")
    Call<PagedResult<Event>> getEvents(@Query("$orderby") String orderBy, @Query("$filter") String filter,
            @Query("$select") String select, @Query("$top") Integer maxResults);

    @GET("/v1.0/me/contacts")
    Call<PagedResult<Contact>> getContacts(
        @Query("$orderby") String orderBy,
            @Query("$select") String select
   );
}

