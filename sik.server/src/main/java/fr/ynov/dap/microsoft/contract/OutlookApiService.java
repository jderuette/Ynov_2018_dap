package fr.ynov.dap.microsoft.contract;

import fr.ynov.dap.microsoft.model.Message;
import fr.ynov.dap.microsoft.model.OutlookContact;
import fr.ynov.dap.microsoft.model.OutlookEvent;
import fr.ynov.dap.microsoft.model.OutlookFolder;
import fr.ynov.dap.microsoft.model.OutlookUser;
import fr.ynov.dap.microsoft.model.PagedResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Communicate with Outlook API.
 * @author Kévin Sibué
 *
 */
public interface OutlookApiService {

    /**
     * Return current user.
     * @return Current user
     */
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
     * Get Message (paginated).
     * @param folderId Folder id
     * @param orderBy Order By
     * @param select Select
     * @param maxResults Max results
     * @return Messages
     */
    @GET("/v1.0/me/mailfolders/{folderid}/messages")
    Call<PagedResult<Message>> getMessages(@Path("folderid") String folderId, @Query("$orderby") String orderBy,
            @Query("$select") String select, @Query("$top") Integer maxResults);

    /**
     * Get every event that match filter. Slice by page.
     * @param orderBy Order for result
     * @param filter Filter to apply.
     * @param select Which information to retrieve.
     * @param maxResults Number of event per page.
     * @return List of event
     */
    @GET("/v1.0/me/events")
    Call<PagedResult<OutlookEvent>> getEvents(@Query("$orderby") String orderBy, @Query("$filter") String filter,
            @Query("$select") String select, @Query("$top") Integer maxResults);

    /**
     * Get contacts from Microsoft Graph API.
     * @param orderBy Order for result
     * @param select Which information to retrieve.
     * @return List of contacts
     */
    @GET("/v1.0/me/contacts")
    Call<PagedResult<OutlookContact>> getContacts(@Query("$orderby") String orderBy, @Query("$select") String select);

}
