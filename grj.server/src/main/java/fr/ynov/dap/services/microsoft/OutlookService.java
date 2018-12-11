package fr.ynov.dap.services.microsoft;

import fr.ynov.dap.models.microsoft.*;
import retrofit2.Call;
import retrofit2.http.*;

public interface OutlookService {

    @GET("/v1.0/me")
    Call<OutlookUser> getCurrentUser();

    @GET("/v1.0/me/mailfolders/{folderid}/messages")
    Call<MicrosoftPagedResult<MicrosoftMessage>> getMessages(
            @Path("folderid") String folderId,
            @Query("$orderby") String orderBy,
            @Query("$select") String select,
            @Query("$top") Integer maxResults
    );

    //TODO grj by Djer |API Microsoft| Je pense que l'API de Microsoft peut être mappé sur quelques chose de plus "précise " que "Object"
    @GET("/v1.0/me/mailfolders/inbox")
    Call<Object> getUnread();

  //TODO grj by Djer |API Microsoft| Je pense que l'API de Microsoft peut être mappé sur quelques chose de plus "précise " que "Object"
    @GET("/v1.0/me/contacts?$count=true")
    Call<Object> getNumberContacts();
}
