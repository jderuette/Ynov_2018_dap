package fr.ynov.dap.data.interfaces;

import fr.ynov.dap.data.Contact;
import fr.ynov.dap.data.Event;
import fr.ynov.dap.data.Message;
import fr.ynov.dap.data.OutlookUser;
import fr.ynov.dap.data.PagedResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OutlookServiceInterface {

  @GET("/v1.0/me")
  Call<OutlookUser> getCurrentUser();

  @GET("/v1.0/me/mailfolders/{folderid}/messages")
  Call<PagedResult<Message>> getMessages(
    @Path("folderid") String folderId,
    @Query("$orderby") String orderBy,
    @Query("$select") String select,
    @Query("$top") Integer maxResults
  );
  
  @GET("/v1.0/me/events")
  Call<PagedResult<Event>> getEvents(
        @Query("$orderby") String orderBy,
        @Query("$filter") String filter,
        @Query("$select") String select,
        @Query("$top") Integer maxResults
  );
  
  @GET("/v1.0/me/contacts")
  Call<PagedResult<Contact>> getContacts(
      @Query("$orderby") String orderBy,
      @Query("$select") String select,
      @Query("$top") Integer maxResults
  );
}