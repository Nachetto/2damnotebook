package dao;

import common.Constantes;
import io.vavr.control.Either;
import retrofit2.Call;
import retrofit2.Response;

public abstract class DaoGenerico {
    public <T> Either<String, T> safeApicall(Call<T> call) {
        Either<String, T> resultado = null;
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful() && response.body() != null) resultado = Either.right(response.body());
            else {
                resultado = Either.left(response.errorBody().toString());
            }
        } catch (Exception e) {
            resultado = Either.left(Constantes.ERROR_CONEXION + e.getMessage());
        }
        return resultado;
    }
}
