package fr.ynov.dap.services.microsoft;

import fr.ynov.dap.models.microsoft.*;
import retrofit2.Call;
import retrofit2.http.*;

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

    @GET("/v1.0/me/mailfolders/inbox")
    Call<Object> getUnread();

    @GET("/v1.0/me/contacts?$count=true")
    Call<Object> getNumberContacts();
}
