package dao.retrofit.llamadas;

import dao.retrofit.modelo.*;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
public interface TheOfficeApi {
    @GET("characters")
    Call<ResponseCharacter> getAllCharacters(@Query("limit") int limit);
    @GET("Any")
    Call<ResponseJokeSimple> getAnyJokeSimple(@Query("lang") String lang, @Query("type") String type);
    @GET("Programming")
    Call<ResponseJoke> getProgrammingJoke(@Query("lang") String lang);
    @GET("Any")
    Single<ResponseJoke> getAnyJokeAsync(@Query("lang") String lang);

}
