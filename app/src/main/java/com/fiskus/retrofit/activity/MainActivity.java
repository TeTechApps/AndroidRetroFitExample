package com.fiskus.retrofit.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.fiskus.retrofit.R;
import com.fiskus.retrofit.adapter.MovieAdapter;
import com.fiskus.retrofit.model.Movie;
import com.fiskus.retrofit.model.MovieResponse;
import com.fiskus.retrofit.model.Trailer;
import com.fiskus.retrofit.model.TrailerResponse;
import com.fiskus.retrofit.rest.ApiClient;
import com.fiskus.retrofit.rest.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    // Before Using This Go To https://www.themoviedb.org/account/signup Create A User And Get Your Api Key
     private static final String YOUR_API_KEY = "";
     private static final int MOVIE_ID = 0;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.movies_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<MovieResponse> call = apiService.getNowPlayingMovies(YOUR_API_KEY);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                ArrayList<Movie> movies = response.body().getResults();
                recyclerView.setAdapter(new MovieAdapter(movies, R.layout.list_item, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });

        Call<TrailerResponse> responseCall = apiService.getMovieTrailer(MOVIE_ID, YOUR_API_KEY, "en");
        responseCall.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                ArrayList<Trailer> trailers = response.body().getResults();
                String trailerKey = trailers.get(0).getKey();

                for (int i = 0; i < trailers.size(); i++) {
                    if (trailers.get(i).getType().equals("Trailer")) {
                        trailerKey = trailers.get(i).getKey();
                        break;
                    }
                }

                TextView textView = (TextView) findViewById(R.id.trailer_key);
                textView.setText(trailerKey);
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
                Log.d("t", t.getMessage());
            }
        });
    }
}
