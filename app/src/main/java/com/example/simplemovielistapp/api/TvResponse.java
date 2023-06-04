package com.example.simplemovielistapp.api;

import com.google.gson.annotations.SerializedName;

public class TvResponse {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("first_air_date")
    private String first_air_date;
    @SerializedName("overview")
    private String overview;
    @SerializedName("poster_path")
    private String poster_path;
    @SerializedName("backdrop_path")
    private String backdrop_path;
    @SerializedName("vote_average")
    private String vote_average;

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getFirstAirDate() {
        return first_air_date;
    }
    public String getOverview() {
        return overview;
    }
    public String getPosterPath() {
        return poster_path;
    }
    public String getBackdropPath() {
        return backdrop_path;
    }
    public String getVoteAverage() {
        return vote_average;
    }
}
