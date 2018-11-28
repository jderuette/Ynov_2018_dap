package fr.ynov.dap.dap.microsoft;

import fr.ynov.dap.dap.model.OutlookContact;
import fr.ynov.dap.dap.model.OutlookEvent;
import fr.ynov.dap.dap.model.OutlookMailFolder;
import fr.ynov.dap.dap.model.OutlookMessage;
import fr.ynov.dap.dap.model.OutlookUser;
import fr.ynov.dap.dap.model.OutlookPagedResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * The Interface OutlookService.
 */
public interface OutlookService {

  /**
   * Gets the current user.
   *
   * @return the current user
   */
  @GET("/v1.0/me")
  Call<OutlookUser> getCurrentUser();

  /**
   * Gets the messages.
   *
   * @param folderId the folder id
   * @param orderBy the order by
   * @param select the select
   * @param maxResults the max results
   * @return the messages
   */
  @GET("/v1.0/me/mailfolders/{folderid}/messages")
  Call<OutlookPagedResult<OutlookMessage>> getMessages(
    @Path("folderid") String folderId,
    @Query("$orderby") String orderBy,
    @Query("$select") String select,
    @Query("$top") Integer maxResults
  );
  
  /**
   * Gets the events.
   *
   * @param orderBy the order by
   * @param select the select
   * @param maxResults the max results
   * @return the events
   */
  @GET("/v1.0/me/events")
  Call<OutlookPagedResult<OutlookEvent>> getEvents(
        @Query("$orderby") String orderBy,
        @Query("$select") String select,
        @Query("$top") Integer maxResults
  );
  
  /**
   * Gets the contacts.
   *
   * @param orderBy the order by
   * @param select the select
   * @return the contacts
   */
  @GET("/v1.0/me/contacts")
  Call<OutlookPagedResult<OutlookContact>> getContacts(
      @Query("$orderby") String orderBy,
      @Query("$select") String select
  );
  
  /**
   * Gets the mail folders.
   *
   * @param folderId the folder id
   * @return the mail folders
   */
  @GET("v1.0/me/mailfolders/{folderid}/")
  Call<OutlookMailFolder> getMailFolders(
      @Path("folderid") String folderId
  );
}