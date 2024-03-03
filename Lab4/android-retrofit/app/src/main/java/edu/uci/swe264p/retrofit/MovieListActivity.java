package edu.uci.swe264p.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.*;

public class MovieListActivity extends AppCompatActivity {
	static final String TAG = MainActivity.class.getSimpleName();
    static final String BASE_URL = "https://api.themoviedb.org/3/";
    static final String API_KEY = "5f7cb29d489bbbb86ca6e6d90f422eb2";
    static Retrofit retrofit = null;
	private RecyclerView recyclerView;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Log.d(TAG, "retrofit build");
        }

		MovieApiService movieApiService = retrofit.create(MovieApiService.class);
        Call<TopRatedResponse> call = movieApiService.getTopRatedMovies(API_KEY);

		Log.d(TAG, call.toString());

        call.enqueue(new Callback<TopRatedResponse>() {
            @Override
            public void onResponse(Call<TopRatedResponse> call, Response<TopRatedResponse> response) {
                TopRatedResponse topRatedResponse = response.body();
                List<Movie> MovieList;
                try {
                    Log.d(TAG, topRatedResponse.getTopRatedMovies().toString());

                    MovieList = topRatedResponse.getTopRatedMovies();
                    recyclerView = findViewById(R.id.rvMovieList);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MovieListActivity.this));
                    recyclerView.setAdapter(new MovieListAdapter(MovieList));
                } catch (NullPointerException e) {
                    Log.e(TAG, e.toString());
                }

            }
            @Override
            public void onFailure(Call<TopRatedResponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }
}
