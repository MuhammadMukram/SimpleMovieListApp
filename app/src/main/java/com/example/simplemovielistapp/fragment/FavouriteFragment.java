package com.example.simplemovielistapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.simplemovielistapp.MainActivity;
import com.example.simplemovielistapp.R;
import com.example.simplemovielistapp.adapter.FavouriteAdapter;
import com.example.simplemovielistapp.models.FavouriteModel;
import com.example.simplemovielistapp.sqlitesection.LoadAsyncData;

import java.util.ArrayList;

public class FavouriteFragment extends Fragment {
    private RecyclerView favouriteList_rv;
    private TextView error_message_tv;
    private ProgressBar fav_progressbar;
    FavouriteAdapter favouriteAdapter;
    private ArrayList<FavouriteModel> favouriteModels = new ArrayList<>();
    private ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                loadAllFavourite();
            }
    );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setView(view);

        if (MainActivity.actionBar != null) {
            MainActivity.actionBar.setTitle("Favourite List");
        }

        favouriteList_rv.setHasFixedSize(true);
        favouriteList_rv.setLayoutManager(new LinearLayoutManager(this.getContext()));

        loadAllFavourite();
    }

    private void loadAllFavourite() {
        new LoadAsyncData(this.getContext(), favouriteModels -> {
            if (favouriteModels.size() > 0) {
                this.favouriteModels.addAll(favouriteModels);
                favouriteAdapter = new FavouriteAdapter(favouriteModels, resultLauncher);
                favouriteList_rv.setAdapter(favouriteAdapter);
                error_message_tv.setVisibility(View.GONE);
                fav_progressbar.setVisibility(View.GONE);
            } else {
                favouriteAdapter = new FavouriteAdapter(favouriteModels, resultLauncher);
                favouriteList_rv.setAdapter(favouriteAdapter);
                error_message_tv.setVisibility(View.VISIBLE);
                fav_progressbar.setVisibility(View.GONE);
            }
        }).execute();
    }

    private void setView(View view) {
        favouriteList_rv = view.findViewById(R.id.favouriteList_rv);
        error_message_tv = view.findViewById(R.id.error_message_tv);
        fav_progressbar = view.findViewById(R.id.fav_progressbar);
    }
}