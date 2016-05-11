package x7a.droid.advancedapp.Interface;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import x7a.droid.advancedapp.models.User;
import x7a.droid.advancedapp.models.Users;

/**
 * Created by DroiD on 20/04/2016.
 */
public interface InterfaceApi {
    @GET("/SignupLogin")
    Call<Users> getUsers();

    @GET("/SignupLogin{id}")
    Call<User> getUser(@Path("id") String user_id);

    @PUT("/SignupLogin/{id}")
    Call<User> updateUser(@Path("id") int user_id, @Body User user);

    @POST("/SignupLogin")
    Call<User> saveUser(@Body User user);

    @DELETE("/SignupLogin/{id}")
    Call<User> deleteUser(@Path("id") int user_id);



}
