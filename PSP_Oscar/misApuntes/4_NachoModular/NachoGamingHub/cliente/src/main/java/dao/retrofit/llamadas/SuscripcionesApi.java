package dao.retrofit.llamadas;

import domain.modelo.Suscripcion;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface SuscripcionesApi {

    @GET("suscripciones")
    Single<List<Suscripcion>> getAllSuscripciones();

    @GET("suscripciones/usuario/{uuid}")
    Single<List<Suscripcion>> getAllSuscripcionesByUsuario(@Path("uuid") String uuid);

    @GET("suscripciones/{uuid}")
    Single<Suscripcion> getSuscripcion(@Path("uuid") String uuid);

    @POST("suscripciones")
    Single<Suscripcion> addSuscripcion(@Body Suscripcion suscripcion);

    @PUT("suscripciones/{uuid}")
    Single<Suscripcion> updateSuscripcion(@Path("uuid") String uuid, @Body Suscripcion suscripcionModificada);

    @DELETE("suscripciones/{uuid}")
    Single<Response<Void>> deleteSuscripcion(@Path("uuid") String uuid);

}
