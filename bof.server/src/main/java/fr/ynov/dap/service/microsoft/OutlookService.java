package fr.ynov.dap.service.microsoft;

import fr.ynov.dap.microsoft.Contact;
import fr.ynov.dap.microsoft.Event;
import fr.ynov.dap.microsoft.Message;
import fr.ynov.dap.microsoft.OutlookMailFolder;
import fr.ynov.dap.microsoft.OutlookUser;
import fr.ynov.dap.microsoft.PagedResult;
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
	  @Query("$select") String select,
	  @Query("$top") Integer maxResults
	);
	
	@GET("/v1.0/me/events")
	Call<PagedResult<Event>> getAllEvents(
	);
	
	@GET("/v1.0/me/contacts")
	Call<PagedResult<Contact>> getContacts(
		@Query("$orderby") String orderBy,
	  @Query("$select") String select,
	  @Query("$top") Integer maxResults
	);
	
	@GET("/v1.0/me/contacts")
	Call<PagedResult<Contact>> getAllContacts(
	);
	
	@GET("v1.0/me/mailfolders/{folderid}/")
	Call<OutlookMailFolder> getMailFolders(
	    @Path("folderid") String folderId
	);
	
}
