package dao.retrofit.llamadas;

import domain.modelo.Articulo;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

import java.util.List;

public interface ArticulosApi {
    @GET("articulos")
    Single<List<Articulo>> getAllArticulos();

    @GET("articulos/juego/{uuid}")
    Single<List<Articulo>> getAllArticulosByJuego(String uuid);

    @GET("articulos/usuario/{uuid}")
    Single<List<Articulo>> getAllArticulosByUsuario(String uuid);

    @GET("articulos/{uuid}")
    Single<Articulo> getArticulo(String uuid);

    @POST("articulos")
    Single<Articulo> addArticulo(Articulo articulo);

    @PUT("articulos/{uuid}")
    Single<Articulo> updateArticulo(String uuid, Articulo articuloModificado);

    @DELETE("articulos/{uuid}")
    Single<Response<Void>> deleteArticulo(String uuid);


}