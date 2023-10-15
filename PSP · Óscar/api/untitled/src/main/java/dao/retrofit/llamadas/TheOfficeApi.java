package dao.retrofit.llamadas;

import dao.retrofit.modelo.*;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface TheOfficeApi {
    @GET("characters")
    Call<ResponseCharacter> getAllCharacters(@Query("limit") int limit);
    @GET("characters")
    Call<ResponseCharacter> getAllCharacters();

    @GET("episodes")
    Call<ResponseEpisode> getAllEpisodes(@Query("limit") int limit);
    @GET("episodes")
    Call<ResponseEpisode> getAllEpisodes();
    @GET("episodes")
    Call<ResponseEpisode> getEpisodesBySeason(@Query("season") int season);


    @GET("seasons")
    Call<ResponseSeason> getAllSeasons();

}
