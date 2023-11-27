package dao.retrofit.llamadas;

import domain.modelo.Usuario;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginApi {
    @GET("login")
    Call<Void> login(@Query("user") String user, @Query("password") String password);

    @POST("login")
    Call<Void> register(Usuario u);
}
