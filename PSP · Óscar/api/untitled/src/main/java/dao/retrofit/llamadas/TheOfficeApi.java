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
    @GET("characters")
    Call<ResponseCharacter> getAllCharactersByGender(@Query("gender") String gender);
    @GET("characters")
    Call<ResponseCharacter> getNamedCharacter(@Query("name") String CharacterName);
    @GET("characters")
    Call<ResponseCharacter> getNamedActor(@Query("actor") String ActorName);
    @GET("characters")
    Call<ResponseCharacter> getAllCharactersByJob(@Query("job") String job);
    @GET("characters")
    Call<ResponseCharacter> getAllJobTypes();


    @GET("seasons")
    Call<ResponseSeason> getAllSeasons(@Query("limit") int limit);
    @GET("episodes")
    Call<ResponseEpisode> getAllEpisodes(@Query("limit") int limit);

}
