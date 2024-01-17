package dao;

import domain.errores.ClienteError;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import retrofit2.HttpException;
import retrofit2.Response;

import java.io.IOException;

public abstract class DaoGenerico {
    public <T> Single<Either<ClienteError, T>> safeSingleApicall(Single<T> call) {
        return call
                .subscribeOn(Schedulers.io())
                .map(Either::<ClienteError, T>right)
                .onErrorReturn(throwable -> {
                    if (throwable instanceof HttpException) {
                        HttpException httpException = (HttpException) throwable;
                        String errorMessage = "Error: " + httpException.code();

                        if (httpException.response().errorBody() != null) {
                            errorMessage = httpException.response().errorBody().string();
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
