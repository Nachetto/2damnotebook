package dao.retrofit.llamadas;

import domain.modelo.Suscripcion;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface SuscripcionesApi {

    @GET("suscripciones")
    Call<List<Suscripcion>> getAllSuscripciones();

    @GET("suscripciones/usuario/{uuid}")
    Call<List<Suscripcion>> getAllSuscripcionesByUsuario(@Path("uuid") String uuid);

    @GET("suscripciones/{uuid}")
    Call<Suscripcion> getSuscripcion(@Path("uuid") String uuid);

    @POST("suscripciones")
    Call<Suscripcion> addSuscripcion(@Body Suscripcion suscripcion);

    @PUT("suscripciones/{uuid}")
    Call<Suscripcion> updateSuscripcion(@Path("uuid") String uuid, @Body Suscripcion suscripcionModificada);

    @DELETE("suscripciones/{uuid}")
    Call<Void> deleteSuscripcion(@Path("uuid") String uuid);

}
