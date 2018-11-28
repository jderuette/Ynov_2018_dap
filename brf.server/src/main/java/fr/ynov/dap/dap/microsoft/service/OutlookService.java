package fr.ynov.dap.dap.microsoft.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Florian
 */
public interface OutlookService {

    /**
     * @return .
     */
    @GET("/v1.0/me")
    Call<OutlookUser> getCurrentUser();

    /**
     * @param folderId .
     * @param orderBy .
     * @param select .
     * @param maxResults .
     * @return .
     */
    @GET("/v1.0/me/mailfolders/{folderid}/messages")
    Call<PagedResult<Message>> getMessages(
            @Path("folderid") String folderId,
            @Query("$orderby") String orderBy,
            @Query("$select") String select,
            @Query("$top") Integer maxResults);

    /**
     * @param filter .
     * @return .
     */
    @GET("/v1.0/me/mailfolders/{filter}")
    Call<PagedResult<Message>> getTotalUnreadMails(
            @Path("filter") String filter);

    /**
     * @param orderBy .
     * @param select .
     * @param maxResults .
     * @return .
     */
    @GET("/v1.0/me/events")
    Call<PagedResult<Event>> getEvents(
            @Query("$orderby") String orderBy,
            @Query("$select") String select,
            @Query("$top") Integer maxResults);

    /**
    * @param orderBy .
    * @param select .
    * @param maxResults .
    * @return .
    */
    @GET("/v1.0/me/contacts")
    Call<PagedResult<Contact>> getContacts(
            @Query("$orderby") String orderBy,
            @Query("$select") String select,
            @Query("$top") Integer maxResults);

    /**
    * @param select .
    * @return .
    */
    @GET("/v1.0/me/contacts")
    Call<PagedResult<Contact>> getNbContacts(
            @Query("$select") String select);

}
