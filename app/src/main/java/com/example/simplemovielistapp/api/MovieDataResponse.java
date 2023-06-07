package com.example.simplemovielistapp.api;

import com.example.simplemovielistapp.models.MovieResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieDataResponse {
    @SerializedName("results")
    private List<MovieResponse> results;
    public List<MovieResponse> getData() { return results; }
}
