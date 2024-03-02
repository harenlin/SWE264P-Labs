package edu.uci.swe264p.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiService {
	// Get Movie
	// https://api.themoviedb.org/3/movie/{id}?api_key=apiKey&language=en-US
	@GET("movie/{id}")
	Call<Movie> getMovie(@Path("id") int id, @Query("api_key") String apiKey);

	// Get Top Rated Movies
	// https://api.themoviedb.org/3/movie/top_rated?api_key=apiKey
	@GET("movie/top_rated")
	Call<TopRatedResponse> getTopRatedMovies(@Query("api_key") String apiKey);
}
