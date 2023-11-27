package dao.retrofit.llamadas;

import domain.modelo.Juego;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface JuegosApi {
    @GET("juegos")
    Single<List<Juego>> getAllJuegos();

    @GET("juegos/{id}")
    Single<Juego> getJuego(@Path("id") String id);

    @POST("juegos")
    Single<Juego> addJuego(@Body Juego juego);

    @PUT("juegos/{id}")
    Single<Juego> updateJuego(@Path("id") String id, @Body Juego juegoModificado);

    @DELETE("juegos/{id}")
    Single<Response<Void>> deleteJuego(@Path("id") String id);
}
