package fr.ynov.dap.microsoft;

import fr.ynov.dap.data.microsoft.MicrosoftEvent;
import fr.ynov.dap.data.microsoft.Contact;
import fr.ynov.dap.data.microsoft.Folder;
import fr.ynov.dap.data.microsoft.Message;
import fr.ynov.dap.data.microsoft.OutlookUser;
import fr.ynov.dap.data.microsoft.PagedResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Call outlook api.
 * @author MBILLEMAZ
 *
 */
public interface OutlookApiRequests {

    /**
     * get current user.
     * @return current user
     */
    @GET("/v1.0/me")
    Call<OutlookUser> getCurrentUser();

    /**
     * get message list.
     * @param folderId folder
     * @param orderBy order
     * @param select select
     * @param maxResults results
     * @return list of mail
     */
    @GET("/v1.0/me/Mailfolders/{folderid}/messages")
    Call<PagedResult<Message>> getMessages(@Path("folderid") String folderId, @Query("$orderby") String orderBy,
            @Query("$select") String select, @Query("$top") Integer maxResults);

    /**
     * get folder.
     * @param folderId folder
     * @return folder
     */
    @GET("/v1.0/me/Mailfolders/{folderid}")
    Call<Folder> getFolder(@Path("folderid") String folderId);

    /**
     * get next events.
     * @param orderBy order
     * @param select field to take.
     * @param maxResults number of results.
     * @param filter filter condition.
     * @return list of next events.
     */
    @GET("/v1.0/me/events")
    Call<PagedResult<MicrosoftEvent>> getEvents(@Query("$orderby") String orderBy, @Query("$select") String select,
            @Query("$top") Integer maxResults, @Query("$filter") String filter);

    /**
     * get contact list.
     * @return nb contact
     */
    @GET("/v1.0/me/contacts?$count=true")
    Call<PagedResult<Contact>> getNbContacts(

    );

}