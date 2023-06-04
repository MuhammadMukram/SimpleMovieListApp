package com.example.simplemovielistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.simplemovielistapp.api.ApiConfig;
import com.example.simplemovielistapp.api.MovieDataResponse;
import com.example.simplemovielistapp.api.MovieResponse;
import com.example.simplemovielistapp.api.TvDataResponse;
import com.example.simplemovielistapp.api.TvResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Call<MovieDataResponse> movieClient = ApiConfig.getApiService().getMovie();
        movieClient.enqueue(new Callback<MovieDataResponse>() {
            @Override
            public void onResponse(Call<MovieDataResponse> call, Response<MovieDataResponse> response) {
                if(response.isSuccessful()){
                    System.out.println(response.body().getData());
                    if (response.body().getData() != null) {
                        List<MovieResponse> data = response.body().getData();
                        Log.d("MainActivity", "onResponse: movie list");
                        for (MovieResponse movieDataResponse : data) {
                            Log.d("MainActivity", "onResponse: " + movieDataResponse.getTitle());
                        }
                    } else {
                        Log.e("MainActivity", "onResponse data body is null " + response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieDataResponse> call, Throwable t) {
                Log.d("MainActivity", "onFailure: " + t.getMessage());
            }
        });

        Call<TvDataResponse> tvClient = ApiConfig.getApiService().getTvSeries();
        tvClient.enqueue(new Callback<TvDataResponse>() {
            @Override
            public void onResponse(Call<TvDataResponse> call, Response<TvDataResponse> response) {
                if(response.isSuccessful()){
                    System.out.println(response.body().getData());
                    if (response.body().getData() != null) {
                        List<TvResponse> data = response.body().getData();
                        Log.d("MainActivity", "onResponse: tv list");
                        for (TvResponse tvDataResponse : data) {
                            Log.d("MainActivity", "onResponse: " + tvDataResponse.getName());
                        }
                    } else {
                        Log.e("MainActivity", "onResponse data body is null " + response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<TvDataResponse> call, Throwable t) {
                Log.d("MainActivity", "onFailure: " + t.getMessage());
            }
        });
    }
}