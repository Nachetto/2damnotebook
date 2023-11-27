package dao.retrofit.llamadas;

import domain.modelo.Usuario;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginApi {
    @GET("login")
    Single<Void> login(@Query("user") String user, @Query("password") String password);

    @POST("login")
    Single<Void> register(Usuario u);
}
