package com.example.simplemovielistapp.sqlitesection;

import com.example.simplemovielistapp.models.FavouriteModel;

import java.util.ArrayList;

public interface LoadCallback {
    void postExecute(ArrayList<FavouriteModel> items);
}
