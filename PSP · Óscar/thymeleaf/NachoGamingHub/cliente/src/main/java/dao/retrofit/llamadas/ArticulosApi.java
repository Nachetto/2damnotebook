package dao.retrofit.llamadas;

import domain.modelo.Articulo;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

import java.util.List;

public interface ArticulosApi {
    @GET("articulos")
    Call<List<Articulo>> getAllArticulos();

    @GET("articulos/juego/{uuid}")
    Call<List<Articulo>> getAllArticulosByJuego(String uuid);

    @GET("articulos/usuario/{uuid}")
    Call<List<Articulo>> getAllArticulosByUsuario(String uuid);

    @GET("articulos/{uuid}")
    Call<Articulo> getArticulo(String uuid);

    @POST("articulos")
    Call<Articulo> addArticulo(Articulo articulo);

    @PUT("articulos/{uuid}")
    Call<Articulo> updateArticulo(String uuid, Articulo articuloModificado);

    @DELETE("articulos/{uuid}")
    Call<String> deleteArticulo(String uuid);


}