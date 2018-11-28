package fr.ynov.dap.dap.microsoft.models;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IOutlookService {

    @GET("/v1.0/me")
    Call<OutlookUser> getCurrentUser();

    @GET("/v1.0/me/mailFolders/{folderName}")
    Call<OutlookFolder> getFolder(@Path("folderName") String folderName);

    @GET("/v1.0/me/mailfolders/{folderid}/messages")
    Call<PagedResult<Message>> getMessages(
            @Path("folderid") String folderId,
            @Query("$orderby") String orderBy,
            @Query("$select") String select,
            @Query("$top") Integer maxResults
    );

    @GET("/v1.0/me/events")
    Call<PagedResult<EventMicrosoft>> getEvents(
            @Query("$orderby") String orderBy,
            @Query("$select") String select,
            @Query("$top") Integer maxResults
    );

    @GET("/v1.0/me/contacts")
    Call<PagedResult<Contact>> getContacts(
            @Query("$orderby") String orderBy,
            @Query("$select") String select
    );

}