package dao;

import common.Constantes;
import domain.errores.ClienteError;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;

import java.io.IOException;

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

    public <T> Single<Either<ClienteError, T>> safeSingleApicall(Single<T> call) {
        return call
                .subscribeOn(Schedulers.io())
                .map(Either::<ClienteError, T>right)
                .onErrorReturn(throwable -> {
                    if (throwable instanceof HttpException) {
                        HttpException httpException = (HttpException) throwable;
                        String errorMessage = "Error: " + httpException.code();
                        try {
                            if (httpException.response().errorBody() != null) {
                                errorMessage = httpException.response().errorBody().string();
                            }
                        } catch (IOException e) {
                            // Log error or handle it
                        }
                        return Either.left(new ClienteError(errorMessage));
                    } else {
                        return Either.left(new ClienteError("Error de comunicación: " + throwable.getMessage()));
                    }
                });
    }

    public Single<Either<ClienteError, String>> safeSingleVoidApicall(Single<Response<Void>> call) {
        return call.map(response -> {
                    Either<ClienteError, String> result = Either.right("OK");
                    if (!response.isSuccessful()) {
                        result = Either.left(new ClienteError(response.message()));
                    }
                    return result;
                })
                .subscribeOn(Schedulers.io());
    }

}
