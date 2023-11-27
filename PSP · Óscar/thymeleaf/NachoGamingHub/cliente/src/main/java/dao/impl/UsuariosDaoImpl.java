package dao.impl;

import dao.DaoGenerico;
import dao.UsuariosDao;
import dao.retrofit.llamadas.LoginApi;
import dao.retrofit.llamadas.UsuariosApi;
import domain.errores.ClienteError;
import domain.modelo.Usuario;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class UsuariosDaoImpl  extends DaoGenerico implements UsuariosDao{

    //inyectamos el API de usuarios
    private final LoginApi loginApi;
    private final UsuariosApi usuariosApi;
    @Inject
    public UsuariosDaoImpl(LoginApi loginApi, UsuariosApi usuariosApi) {
        this.loginApi = loginApi;
        this.usuariosApi = usuariosApi;
    }

    @Override
    public Single<Either<ClienteError, List<Usuario>>> getUsuarios() {
        return safeSingleApicall(usuariosApi.getAllUsuarios());
    }

    @Override
    public Single<Either<ClienteError, Usuario>> getUsuario(String uuid){
        return safeSingleApicall(usuariosApi.getUsuario(uuid));
    }

    @Override
    public Single<Either<ClienteError, Usuario>> postUsuario(Usuario usuario) {
        return safeSingleApicall(usuariosApi.addUsuario(usuario));
    }

    @Override
    public Single<Either<ClienteError, Usuario>> putUsuario(Usuario usuario) {
        return safeSingleApicall(usuariosApi.updateUsuario(usuario.getUuid().toString(), usuario));
    }

    @Override
    public Single<Either<ClienteError, String>> deleteUsuario(String id) {
        return safeSingleVoidApicall(usuariosApi.deleteUsuario(id));
    }

    @Override
    public Single<Either<ClienteError, String>> login(String user, String password) {
        return safeSingleVoidApicall(loginApi.login(user, password));
    }

    @Override
    public Single<Either<ClienteError, String>> register(Usuario u) {
        return safeSingleVoidApicall(loginApi.register(u));
    }
}