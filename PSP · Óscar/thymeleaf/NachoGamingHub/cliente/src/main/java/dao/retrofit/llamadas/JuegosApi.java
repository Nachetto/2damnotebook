package dao.retrofit.llamadas;

import domain.modelo.Juego;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface JuegosApi {
    @GET("juegos")
    Call<List<Juego>> getAllJuegos();

    @GET("juegos/{id}")
    Call<Juego> getJuego(@Path("id") String id);

    @POST("juegos")
    Call<Juego> addJuego(@Body Juego juego);

    @PUT("juegos/{id}")
    Call<Juego> updateJuego(@Path("id") String id, @Body Juego juegoModificado);

    @DELETE("juegos/{id}")
    Call<Void> deleteJuego(@Path("id") String id);
}
