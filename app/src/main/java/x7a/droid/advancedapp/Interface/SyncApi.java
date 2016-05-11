package x7a.droid.advancedapp.Interface;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import x7a.droid.advancedapp.models.SyncTransaction;
import x7a.droid.advancedapp.models.SyncTransactions;

/**
 * Created by DroiD on 04/05/2016.
 */
public interface SyncApi {
    @GET("/Task4")
    Call<SyncTransactions> getExpenses();

    @GET("/Task4{id}")
    Call<SyncTransaction> getExpenses(@Path("id") String expenses_id);

    @PUT("/Task4/{id}")
    Call<SyncTransaction> updateExpenses(@Path("id")int expenses_id, @Body SyncTransaction expenses);

    @POST("/Task4")
    Call<SyncTransaction> saveExpenses(@Body SyncTransaction expenses);

    @DELETE("/Task4/{id}")
    Call<SyncTransaction> deleteExpenses(@Path("id")int expenses_id);

    @POST("/Task4")
    Call<SyncTransaction> syncExpenses(@Body SyncTransaction expenses);
}
