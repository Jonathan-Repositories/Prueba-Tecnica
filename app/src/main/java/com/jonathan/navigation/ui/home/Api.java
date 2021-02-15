package com.jonathan.navigation.ui.home;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    @GET("/3/movie/{movie_id}")
    Call<Pelicula> getPelicula(
            @Path("movie_id") String movie_id,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );
    //https://api.themoviedb.org/3/movie/464052?api_key=41c57e0ad14b00dcd5801ed7cf4a42a0&language=en-US
}
