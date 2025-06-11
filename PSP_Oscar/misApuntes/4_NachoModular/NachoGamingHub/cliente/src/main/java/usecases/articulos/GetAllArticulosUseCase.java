package usecases.articulos;

import dao.impl.ArticulosDaoImpl;
import domain.errores.ClienteError;
import domain.modelo.Articulo;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class GetAllArticulosUseCase {
    private final ArticulosDaoImpl articulosDao;
    @Inject
    public GetAllArticulosUseCase(ArticulosDaoImpl articulosDao) {
        this.articulosDao = articulosDao;
    }

    public Single<Either<ClienteError, List<Articulo>>> getAllArticulos() {
        return articulosDao.getAllArticulos();
    }
}
