package fr.ynov.dap.microsoft.service;

import fr.ynov.dap.microsoft.data.Contact;
import fr.ynov.dap.microsoft.data.Event;
import fr.ynov.dap.microsoft.data.Message;
import fr.ynov.dap.microsoft.data.OutlookUser;
import fr.ynov.dap.microsoft.data.PagedResult;
import fr.ynov.dap.microsoft.data.UnreadmailResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Mon_PC
 */
public interface OutlookService {
    /**
     * @return currentUser
     */
    @GET("/v1.0/me")
    Call<OutlookUser> getCurrentUser();

    /**
     * @param folderId (inbox, trash, ...)
     * @param orderBy query order
     * @param select query select
     * @param maxResults nombre de resultats
     * @return messages
     */
    @GET("/v1.0/me/mailfolders/{folderid}/messages")
    Call<PagedResult<Message>> getMessages(@Path("folderid") String folderId, @Query("$orderby") String orderBy,
            @Query("$select") String select, @Query("$top") Integer maxResults);

    /**
     * @param folderId (inbox, trash,...)
     * @return unread messages
     */
    @GET("/v1.0/me/mailfolders/{folderid}")
    Call<UnreadmailResult> getUnreadMessages(@Path("folderid") String folderId);

    /**
     * @param orderBy query order
     * @param select query select
     * @param maxResults nombre de resultats
     * @param filter filtre
     * @return events
     */
    @GET("/v1.0/me/events")
    Call<PagedResult<Event>> getEvents(@Query("$orderby") String orderBy, @Query("$select") String select,
            @Query("$top") Integer maxResults, @Query("$filter") String filter);

    /**
     * @param orderBy query order
     * @param select query select
     * @return Contact
     */
    @GET("/v1.0/me/contacts")
    Call<PagedResult<Contact>> getContacts(@Query("$orderby") String orderBy, @Query("$select") String select);

}
