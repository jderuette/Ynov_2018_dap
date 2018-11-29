package fr.ynov.dap.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Utilise retrofit pour récupérer les données microsoft.
 * @author abaracas
 *
 */
public interface OutlookService {
	
	@GET("/api/v2.0/me")
	Call<OutlookUserService> getCurrentUser();

	@GET("/api/v2.0/me/mailfolders/{folderid}/messages")
	Call<MicrosoftPagedResultService<OutlookMessageService>> getMessages(
	  @Path("folderid") String folderId,
	  @Query("$orderby") String orderBy,
	  @Query("$select") String select,
	  @Query("$top") Integer maxResults
	);
	
	@GET("/api/v2.0/me/events")
	Call<MicrosoftPagedResultService<OutlookEventService>> getEvents(
	  @Query("$orderby") String orderBy,
	  @Query("$select") String select,
	  @Query("$top") Integer maxResults
	);
	
	@GET("/api/v2.0/me/contacts")
	Call<MicrosoftPagedResultService<OutlookContactService>> getContacts(
	  @Query("$orderby") String orderBy,
	  @Query("$select") String select,
	  @Query("$top") Integer maxResults
	);
}
