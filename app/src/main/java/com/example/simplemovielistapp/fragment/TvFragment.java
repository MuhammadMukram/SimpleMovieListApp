package com.example.simplemovielistapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.simplemovielistapp.R;
import com.example.simplemovielistapp.adapter.TvAdapter;
import com.example.simplemovielistapp.api.ApiConfig;
import com.example.simplemovielistapp.api.TvDataResponse;
import com.example.simplemovielistapp.api.TvResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvFragment extends Fragment {

    private RecyclerView tvList_rv;
    private ProgressBar tv_progressbar;
    private TextView error_message_tv;
    public static List<TvResponse> dataTv = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_progressbar = view.findViewById(R.id.tv_progressbar);
        error_message_tv = view.findViewById(R.id.error_message_tv);
        tvList_rv = view.findViewById(R.id.tvList_rv);

        tvList_rv.setHasFixedSize(true);
        tvList_rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        setDataTv();

    }

    private void setDataTv() {
        Call<TvDataResponse> tvClient = ApiConfig.getApiService().getTvSeries();
        tvClient.enqueue(new Callback<TvDataResponse>() {
            @Override
            public void onResponse(Call<TvDataResponse> call, Response<TvDataResponse> response) {
                if(response.isSuccessful()){
                    if (response.body().getData() != null) {
                        tv_progressbar.setVisibility(View.GONE);
                        dataTv = response.body().getData();
                        Log.d("MainActivity", "onResponse: tv list");
                        for (TvResponse tvDataResponse : dataTv) {
                            Log.d("MainActivity", "onResponse: " + tvDataResponse.getName());
                        }
                        TvAdapter tvAdapter = new TvAdapter(dataTv);
                        tvList_rv.setAdapter(tvAdapter);
                    } else {
                        Log.e("MainActivity", "onResponse data body is null");
                        tv_progressbar.setVisibility(View.GONE);
                        error_message_tv.setVisibility(View.VISIBLE);
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