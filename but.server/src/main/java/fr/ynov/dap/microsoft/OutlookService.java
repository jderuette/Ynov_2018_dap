package fr.ynov.dap.microsoft;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Interface to manage api outlook route with microsoft.
 * @author thibault
 *
 */
public interface OutlookService {

    /**
     * Route to get the current user.
     * @return the current user.
     */
    @GET("/v1.0/me")
    Call<OutlookUser> getCurrentUser();

    /**
     * Get one folder.
     * @param folderId the folder id.
     * @return folder.
     */
    @GET("/v1.0/me/mailfolders/{folderid}")
    Call<Folder> getFolder(
        @Path("folderid") String folderId
    );

    /**
     * Get all messages of one folder.
     * @param folderId the folder id
     * @param orderBy order of message
     * @param select select the fields
     * @param maxResults number of max result
     * @return the messages
     */
    @GET("/v1.0/me/mailfolders/{folderid}/messages")
    Call<PagedResult<Message>> getMessages(
        @Path("folderid") String folderId,
        @Query("$orderby") String orderBy,
        @Query("$select") String select,
        @Query("$top") Integer maxResults
    );

    /**
     * Get contacts of user (and count total).
     * @param orderBy order of contact
     * @param select the selected fields
     * @param maxResults max result
     * @return the contacts.
     */
    @GET("/v1.0/me/contacts?$count=true")
    //TODO but by Djer |POO| Renome cette méthode, son nom est confusant
    Call<PagedResult<Contact>> getContacts(
        @Query("$orderby") String orderBy,
        @Query("$select") String select,
        @Query("$top") Integer maxResults
    );

    /**
     * Get events on user.
     * @param orderBy oder events
     * @param select selected fields
     * @param maxResults max result
     * @param filter the filter
     * @return the events.
     */
    @GET("/v1.0/me/events")
    Call<PagedResult<Event>> getEvents(
          @Query("$orderby") String orderBy,
          @Query("$select") String select,
          @Query("$top") Integer maxResults,
          @Query("$filter") String filter
    );
}
