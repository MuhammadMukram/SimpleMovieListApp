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
import com.example.simplemovielistapp.adapter.TvAdapter;
import com.example.simplemovielistapp.api.ApiConfig;
import com.example.simplemovielistapp.api.TvDataResponse;
import com.example.simplemovielistapp.models.MovieResponse;
import com.example.simplemovielistapp.models.TvResponse;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvFragment extends Fragment {

    private RecyclerView tvList_rv;
    private ProgressBar tv_progressbar;
    private TextView error_message_tv;
    private ImageView refresh_iv, clearIcon_iv;
    private TextInputEditText searchBar_et;
    private RelativeLayout searchBar_rl;
    private TvAdapter tvAdapter;
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
        refresh_iv = view.findViewById(R.id.refresh_iv);
        searchBar_et = view.findViewById(R.id.searchBar_et);
        searchBar_rl = view.findViewById(R.id.searchBar_rl);
        clearIcon_iv = view.findViewById(R.id.clearIcon_iv);

        if (MainActivity.actionBar != null) {
            MainActivity.actionBar.setTitle("Tv Show List");
        }

        tvList_rv.setHasFixedSize(true);
        tvList_rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        setDataTv();

        refresh_iv.setOnClickListener(v -> {
            tv_progressbar.setVisibility(View.VISIBLE);
            error_message_tv.setVisibility(View.GONE);
            refresh_iv.setVisibility(View.GONE);
            setDataTv();
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
                searchTv(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void searchTv(CharSequence s) {
        String searchBar = s.toString().toLowerCase().trim();
        List<TvResponse> dataTvSearch = new ArrayList<>();
        if (searchBar.isEmpty()) {
            tvAdapter = new TvAdapter(dataTv);
            tvList_rv.setAdapter(tvAdapter);
        } else {
            for (TvResponse tv : dataTv) {
                if (tv.getName().toLowerCase().contains(searchBar)) {
                    dataTvSearch.add(tv);
                }
            }
            tvAdapter = new TvAdapter(dataTvSearch);
            tvList_rv.setAdapter(tvAdapter);
        }
    }

    private void setDataTv() {
        Call<TvDataResponse> tvClient = ApiConfig.getApiService().getTvSeries();
        tvClient.enqueue(new Callback<TvDataResponse>() {
            @Override
            public void onResponse(Call<TvDataResponse> call, Response<TvDataResponse> response) {
                if(response.isSuccessful()){
                    if (response.body().getData() != null) {
                        searchBar_rl.setVisibility(View.VISIBLE);
                        tv_progressbar.setVisibility(View.GONE);
                        dataTv = response.body().getData();

                        tvAdapter = new TvAdapter(dataTv);
                        tvList_rv.setAdapter(tvAdapter);
                    } else {
                        Toast.makeText(getContext(), "Data is Empty", Toast.LENGTH_SHORT).show();
                        tv_progressbar.setVisibility(View.GONE);
                        error_message_tv.setVisibility(View.VISIBLE);
                        refresh_iv.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<TvDataResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to Load Data", Toast.LENGTH_SHORT).show();
                tv_progressbar.setVisibility(View.GONE);
                error_message_tv.setVisibility(View.VISIBLE);
                refresh_iv.setVisibility(View.VISIBLE);
            }
        });
    }
}