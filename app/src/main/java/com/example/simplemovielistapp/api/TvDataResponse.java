package com.example.simplemovielistapp.api;

import com.example.simplemovielistapp.models.TvResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvDataResponse {
    @SerializedName("results")
    private List<TvResponse> results;
    public List<TvResponse> getData() { return results; }
}
