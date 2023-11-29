package dao.retrofit.llamadas;

import domain.modelo.Usuario;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface UsuariosApi {

    @GET("usuarios")
    Single<List<Usuario>> getAllUsuarios();

    @GET("usuarios/{id}")
    Single<Usuario> getUsuario(@Path("id") String id);

    @GET("login/{name}")
    Single<Usuario> getUsuarioFromUserName(@Path("name") String name);

    @POST("usuarios")
    Single<Usuario> addUsuario(@Body Usuario usuario);

    @PUT("usuarios/{id}")
    Single<Usuario> updateUsuario(@Path("id") String id, @Body Usuario modifiedUsuario);

    @DELETE("usuarios/{id}")
    Single<Response<Void>> deleteUsuario(@Path("id") String id);
}