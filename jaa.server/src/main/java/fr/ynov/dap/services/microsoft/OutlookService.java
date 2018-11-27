package fr.ynov.dap.services.microsoft;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Retrofit interface of OutlookService. This is used to do api request to the Microsoft Graph API.
 */
public interface OutlookService {

    /**
     * Get current user.
     * @return the curent user.
     */
    @GET("/v1.0/me")
    Call<OutlookUser> getCurrentUser();

    /**
     * Get the messages (emails).
     * @param folderId the folder id.
     * @param orderBy sort results in a particular order.
     * @param select select some properties.
     * @param maxResults number of maximum results.
     * @return Microsoft Emails
     */
    @GET("/v1.0/me/mailfolders/{folderid}/messages")
    Call<PagedResult<Message>> getMessages(
      @Path("folderid") String folderId,
      @Query("$orderby") String orderBy,
      @Query("$select") String select,
      @Query("$top") Integer maxResults
    );

    /**
     * Get the messages (emails).
     * @param folderId the folder id.
     * @param orderBy sort results in a particular order.
     * @param select select some properties.
     * @param maxResults number of maximum results.
     * @param filter filter results.
     * @param count if enabled, it adds a count property that display the number of total results.
     * @return Microsoft Emails
     */
    @GET("/v1.0/me/mailfolders/{folderid}/messages")
    Call<PagedResult<Message>> getMessages(
      @Path("folderid") String folderId,
      @Query("$orderby") String orderBy,
      @Query("$select") String select,
      @Query("$top") Integer maxResults,
      @Query("$filter") String filter,
      @Query("$count") Boolean count
    );

    /**
     * Get the Microsoft events.
     * @param orderBy sort results in a particular order.
     * @param select select some properties.
     * @param maxResults number of maximum results.
     * @return Microsoft events.
     */
    @GET("/v1.0/me/events")
    Call<PagedResult<Event>> getEvents(
          @Query("$orderby") String orderBy,
          @Query("$select") String select,
          @Query("$top") Integer maxResults
    );

    /**
     * Get the Microsoft contacts (people).
     * @param orderBy sort results in a particular order.
     * @param select select some properties.
     * @param maxResults number of maximum results.
     * @param count if enabled, it adds a count property that display the number of total results.
     * @return Microsoft contacts.
     */
    @GET("/v1.0/me/contacts")
    Call<PagedResult<Contact>> getContacts(
        @Query("$orderby") String orderBy,
        @Query("$select") String select,
        @Query("$top") Integer maxResults,
        @Query("$count") Boolean count
    );
  }
