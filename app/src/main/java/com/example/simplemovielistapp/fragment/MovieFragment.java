package com.example.simplemovielistapp.fragment;

import static android.content.ContentValues.TAG;

import android.app.ActionBar;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.simplemovielistapp.MainActivity;
import com.example.simplemovielistapp.R;
import com.example.simplemovielistapp.adapter.MovieAdapter;
import com.example.simplemovielistapp.api.ApiConfig;
import com.example.simplemovielistapp.api.MovieDataResponse;
import com.example.simplemovielistapp.models.MovieResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieFragment extends Fragment {

    private RecyclerView movieList_rv;
    private ProgressBar movie_progressbar;
    private TextView error_message_tv;
    private MovieAdapter movieAdapter;
    private List<MovieResponse> dataMovie = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(MovieFragment.class.getSimpleName(), "masuk home fragment");

        movieList_rv = view.findViewById(R.id.movieList_rv);
        movie_progressbar = view.findViewById(R.id.movie_progressbar);
        error_message_tv = view.findViewById(R.id.error_message_tv);

        Log.d(TAG, "otw masuk require Action bar");
        if (MainActivity.actionBar != null) {
            Log.d(TAG, "masuk require Action bar");
            MainActivity.actionBar.setTitle("Movie List");
        }

        movieList_rv.setHasFixedSize(true);
        movieList_rv.setLayoutManager(new GridLayoutManager(getContext(), 2));

        setDataMovie();
    }

    private void setDataMovie() {
        Call<MovieDataResponse> movieClient = ApiConfig.getApiService().getMovie();
        movieClient.enqueue(new Callback<MovieDataResponse>() {
            @Override
            public void onResponse(Call<MovieDataResponse> call, Response<MovieDataResponse> response) {
                if(response.isSuccessful()){
                    if (response.body().getData() != null) {
                        movie_progressbar.setVisibility(View.GONE);
                        dataMovie = response.body().getData();
                        Log.d("MainActivity", "onResponse: movie list");
                        for (MovieResponse movieDataResponse : dataMovie) {
                            Log.d("MainActivity", "onResponse: " + movieDataResponse.getTitle());
                        }
                        movieAdapter = new MovieAdapter(dataMovie);
                        movieList_rv.setAdapter(movieAdapter);
                    } else {
                        Log.e("MainActivity", "onResponse data body is null " + response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieDataResponse> call, Throwable t) {
                Log.d("MainActivity", "onFailure: " + t.getMessage());
                error_message_tv.setVisibility(View.VISIBLE);
            }
        });
    }

}