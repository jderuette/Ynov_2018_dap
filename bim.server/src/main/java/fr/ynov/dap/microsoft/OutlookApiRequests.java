package fr.ynov.dap.microsoft;

import fr.ynov.dap.data.microsoft.Folder;
import fr.ynov.dap.data.microsoft.Message;
import fr.ynov.dap.data.microsoft.OutlookUser;
import fr.ynov.dap.data.microsoft.PagedResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Call outlook api.
 * @author MBILLEMAZ
 *
 */
public interface OutlookApiRequests {

    /**
     * get current user.
     * @return current user
     */
    @GET("/v1.0/me")
    Call<OutlookUser> getCurrentUser();

    /**
     * get message list.
     * @param folderId
     * @param orderBy
     * @param select
     * @param maxResults
     * @return
     */
    @GET("/v1.0/me/Mailfolders/{folderid}/messages")
    Call<PagedResult<Message>> getMessages(@Path("folderid") String folderId, @Query("$orderby") String orderBy,
            @Query("$select") String select, @Query("$top") Integer maxResults);

    /**
     * get folder.
     * @param folderId
     * @return
     */
    @GET("/v1.0/me/Mailfolders/{folderid}")
    Call<Folder> getFolder(@Path("folderid") String folderId);

}