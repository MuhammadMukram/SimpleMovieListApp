package com.example.simplemovielistapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simplemovielistapp.MainActivity;
import com.example.simplemovielistapp.R;
import com.example.simplemovielistapp.adapter.MovieAdapter;
import com.example.simplemovielistapp.api.ApiConfig;
import com.example.simplemovielistapp.api.MovieDataResponse;
import com.example.simplemovielistapp.models.MovieResponse;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieFragment extends Fragment {

    private RecyclerView movieList_rv;
    private ProgressBar movie_progressbar;
    private TextView error_message_tv;
    private ImageView refresh_iv, clearIcon_iv;
    private MovieAdapter movieAdapter;
    private List<MovieResponse> dataMovie = new ArrayList<>();
    private TextInputEditText searchBar_et;
    private RelativeLayout searchBar_rl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        movieList_rv = view.findViewById(R.id.movieList_rv);
        movie_progressbar = view.findViewById(R.id.movie_progressbar);
        error_message_tv = view.findViewById(R.id.error_message_tv);
        refresh_iv = view.findViewById(R.id.refresh_iv);
        searchBar_et = view.findViewById(R.id.searchBar_et);
        searchBar_rl = view.findViewById(R.id.searchBar_rl);
        clearIcon_iv = view.findViewById(R.id.clearIcon_iv);

        if (MainActivity.actionBar != null) {
            MainActivity.actionBar.setTitle("Movie List");
        }

        movieList_rv.setHasFixedSize(true);
        movieList_rv.setLayoutManager(new GridLayoutManager(getContext(), 2));

        setDataMovie();

        refresh_iv.setOnClickListener(v -> {
            movie_progressbar.setVisibility(View.VISIBLE);
            refresh_iv.setVisibility(View.GONE);
            error_message_tv.setVisibility(View.GONE);
            setDataMovie();
        });

        clearIcon_iv.setOnClickListener(v -> {
            searchBar_et.setText("");
            clearIcon_iv.setVisibility(View.GONE);
        });

        searchBar_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                clearIcon_iv.setVisibility(View.VISIBLE);
                searchMovie(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void searchMovie(CharSequence s) {
        String searchBar = s.toString().toLowerCase().trim();
        List<MovieResponse> dataMovieSearch = new ArrayList<>();
        if (searchBar.isEmpty()) {
            movieAdapter = new MovieAdapter(dataMovie);
            movieList_rv.setAdapter(movieAdapter);
        } else {
            for (MovieResponse movie : dataMovie) {
                if (movie.getTitle().toLowerCase().contains(searchBar)) {
                    dataMovieSearch.add(movie);
                }
            }
            movieAdapter = new MovieAdapter(dataMovieSearch);
            movieList_rv.setAdapter(movieAdapter);
        }
    }

    private void setDataMovie() {
        Call<MovieDataResponse> movieClient = ApiConfig.getApiService().getMovie();
        movieClient.enqueue(new Callback<MovieDataResponse>() {
            @Override
            public void onResponse(Call<MovieDataResponse> call, Response<MovieDataResponse> response) {
                if(response.isSuccessful()){
                    if (response.body().getData() != null) {
                        searchBar_rl.setVisibility(View.VISIBLE);
                        movie_progressbar.setVisibility(View.GONE);
                        dataMovie = response.body().getData();

                        movieAdapter = new MovieAdapter(dataMovie);
                        movieList_rv.setAdapter(movieAdapter);
                    } else {
                        Toast.makeText(getContext(), "Data is Empty", Toast.LENGTH_SHORT).show();
                        movie_progressbar.setVisibility(View.GONE);
                        refresh_iv.setVisibility(View.VISIBLE);
                        error_message_tv.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieDataResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to Load Data", Toast.LENGTH_SHORT).show();
                movie_progressbar.setVisibility(View.GONE);
                refresh_iv.setVisibility(View.VISIBLE);
                error_message_tv.setVisibility(View.VISIBLE);
            }
        });
    }

}