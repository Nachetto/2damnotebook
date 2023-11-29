package dao.retrofit;


import com.squareup.moshi.Moshi;
import common.config.Configuracion;
import dao.retrofit.llamadas.*;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.time.Duration;
import java.time.temporal.ChronoUnit;


public class ProducesRetrofit {

    @Produces
    @Singleton
    public Moshi getMoshi() {
        return new Moshi.Builder()
                .add(new UUIDJsonAdapter())
                .add(new LocalDateJsonAdapter())
                .build();
    }


    @Produces
    public OkHttpClient getOK() {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        return new OkHttpClient.Builder()
                .readTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .callTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .connectTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .connectionPool(new ConnectionPool(1, 1, java.util.concurrent.TimeUnit.SECONDS))
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .build();
    }

    @Produces
    @Singleton
    public Retrofit retrofits(OkHttpClient clientOK, Moshi moshi, Configuracion config) {
        clientOK.newBuilder()
                .connectionPool(new okhttp3.ConnectionPool(1, 2, java.util.concurrent.TimeUnit.SECONDS))
                .build();

        return new Retrofit.Builder()
                .baseUrl(config.getBaseUrl())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
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
