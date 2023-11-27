package dao.retrofit.llamadas;

import domain.modelo.Usuario;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface UsuariosApi {

    @GET("usuarios")
    Call<List<Usuario>> getAllUsuarios();

    @GET("usuarios/{id}")
    Call<Usuario> getUsuario(@Path("id") String id);

    @POST("usuarios")
    Call<Usuario> addUsuario(@Body Usuario usuario);

    @PUT("usuarios/{id}")
    Call<Usuario> updateUsuario(@Path("id") String id, @Body Usuario modifiedUsuario);

    @DELETE("usuarios/{id}")
    Call<Void> deleteUsuario(@Path("id") String id);

}