package com.ynov.dap.service.microsoft;

import com.ynov.dap.model.microsoft.Contact;
import com.ynov.dap.model.microsoft.Event;
import com.ynov.dap.model.microsoft.Folder;
import com.ynov.dap.model.microsoft.Message;
import com.ynov.dap.model.microsoft.OutlookUser;
import com.ynov.dap.model.microsoft.PagedResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OutlookService {

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
  
  @GET("/v1.0/me/events")
  Call<PagedResult<Event>> getEvents(
        @Query("$orderby") String orderBy,
        @Query("$select") String select,
        @Query("$top") Integer maxResults
  );
  
  @GET("/v1.0/me/contacts")
  Call<PagedResult<Contact>> getContacts(
      @Query("$orderby") String orderBy,
      @Query("$select") String select,
      @Query("$top") Integer maxResults
  );
  
  @GET("/v1.0/me/mailFolders/{folderName}")
  Call<Folder> getFolder(@Path("folderName") String folderName);
  
  @GET("/v1.0/me/contacts?$count=true")
  Call<PagedResult<Contact>> getNbContacts();
}