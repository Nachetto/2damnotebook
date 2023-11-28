package usecases.usuarios;

import dao.impl.UsuariosDaoImpl;
import jakarta.inject.Inject;

public class GetIdFromUserNameUseCase {

    private final UsuariosDaoImpl dao;
    @Inject
    public GetIdFromUserNameUseCase(UsuariosDaoImpl dao) {
        this.dao = dao;
    }

    public int getIdFromUserName(String userName) {
        return dao.getIdFromUserName(userName);
    }
}
