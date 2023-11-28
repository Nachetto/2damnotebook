package dao.retrofit.llamadas;

import domain.modelo.Usuario;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;
import java.util.UUID;

public interface UsuariosApi {

    @GET("usuarios")
    Single<Either<String,List<Usuario>>> getAllUsuarios();

    @GET("usuarios/{id}")
    Single<Usuario> getUsuario(@Path("id") String id);

    @POST("usuarios")
    Single<Usuario> addUsuario(@Body Usuario usuario);

    @PUT("usuarios/{id}")
    Single<Usuario> updateUsuario(@Path("id") String id, @Body Usuario modifiedUsuario);

    @DELETE("usuarios/{id}")
    Single<Response<Void>> deleteUsuario(@Path("id") String id);


    Single<UUID> getUsuarioFromUserName(String userName);
}