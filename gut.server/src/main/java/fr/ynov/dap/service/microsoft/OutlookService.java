package fr.ynov.dap.service.microsoft;

import fr.ynov.dap.data.microsoft.MicrosoftContact;
import fr.ynov.dap.data.microsoft.MicrosoftEvent;
import fr.ynov.dap.data.microsoft.MicrosoftMessage;
import fr.ynov.dap.data.microsoft.MicrosoftAccount;
import fr.ynov.dap.service.microsoft.helper.PagedResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OutlookService {

	@GET("/api/v2.0/me")
	Call<MicrosoftAccount> getCurrentUser();

	@GET("/api/v2.0/me/mailfolders/{folderid}/messages")
	Call<PagedResult<MicrosoftMessage>> getMessages(@Path("folderid") String folderId,
			@Query("$orderby") String orderBy, @Query("$select") String select, @Query("$top") Integer maxResults);

	@GET("/api/v2.0/me/events")
	Call<PagedResult<MicrosoftEvent>> getEvents(@Query("$orderby") String orderBy, @Query("$select") String select,
			@Query("$top") Integer maxResults);
	
	@GET("/api/v2.0/me/contacts")
	Call<PagedResult<MicrosoftContact>> getContacts(
	    @Query("$orderby") String orderBy,
	    @Query("$select") String select,
	    @Query("$top") Integer maxResults
	);
}