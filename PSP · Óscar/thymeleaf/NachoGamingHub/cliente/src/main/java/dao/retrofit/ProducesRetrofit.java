package dao.retrofit;


import com.squareup.moshi.Moshi;
import common.config.Configuracion;
import dao.retrofit.llamadas.*;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;


public class ProducesRetrofit {

    @Produces
    @Singleton
    public Moshi getMoshi() {
        return new Moshi.Builder().build();
    }


    @Produces
    public OkHttpClient getOK() {
        return new OkHttpClient.Builder()
                .connectionPool(new okhttp3.ConnectionPool(1, 1, java.util.concurrent.TimeUnit.SECONDS)).build();
    }

    @Produces
    @Singleton
    public Retrofit retrofits(OkHttpClient clientOK, Moshi moshi, Configuracion config) {
        return new Retrofit.Builder()
                .baseUrl(config.getBaseUrl())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(clientOK)
                .build();
    }

    @Produces
    public ArticulosApi getArticulosApi(Retrofit retrofit) {
        return retrofit.create(ArticulosApi.class);
    }

    @Produces
    public UsuariosApi getUsuariosApi(Retrofit retrofit) {
        return retrofit.create(UsuariosApi.class);
    }

    @Produces
    public JuegosApi getJuegosApi(Retrofit retrofit) {
        return retrofit.create(JuegosApi.class);
    }

    @Produces
    public LoginApi getLoginApi(Retrofit retrofit) {
        return retrofit.create(LoginApi.class);
    }

    @Produces
    public SuscripcionesApi getSuscripcionesApi(Retrofit retrofit) {
        return retrofit.create(SuscripcionesApi.class);
    }
}
