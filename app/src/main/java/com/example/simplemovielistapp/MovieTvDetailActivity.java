package com.example.simplemovielistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.simplemovielistapp.api.MovieResponse;
import com.example.simplemovielistapp.api.TvResponse;

public class MovieTvDetailActivity extends AppCompatActivity {
    public static final String EXTRA_ITEM = "item_extra";
    public static final String EXTRA_TYPE = "type_extra";
    public static final int TYPE_MOVIE = 1;
    public static final int TYPE_TV = 2;
    private final String imgBaseUrl = "https://image.tmdb.org/t/p/w500";
    private ImageView backdrop_image_iv, poster_image_iv, type_icon_iv;
    private CardView back_button, favourite_button;
    private TextView title_tv, release_date_tv, rating_tv, overview_tv, type_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_tv_detail);
        setView();

        int type = getIntent().getIntExtra(EXTRA_TYPE, 0);

        if (type == TYPE_MOVIE) {
            MovieResponse movieResponse = getIntent().getParcelableExtra(EXTRA_ITEM);
            displayMovieView(movieResponse);
        }

        if (type == TYPE_TV) {
            TvResponse tvResponse = getIntent().getParcelableExtra(EXTRA_ITEM);
            displayTvView(tvResponse);
        }
    }

    private void displayTvView(TvResponse tvResponse) {
        Glide.with(this)
                .load(imgBaseUrl + tvResponse.getBackdropPath())
                .centerCrop()
                .into(backdrop_image_iv);
        Glide.with(this)
                .load(imgBaseUrl + tvResponse.getPosterPath())
                .centerCrop()
                .into(poster_image_iv);
        title_tv.setText(tvResponse.getName());
        release_date_tv.setText(tvResponse.getFirstAirDate().substring(0, 4));
        rating_tv.setText(String.valueOf(tvResponse.getVoteAverage()));
        overview_tv.setText(tvResponse.getOverview());
        type_icon_iv.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.tv_icon, null));
        type_tv.setText(R.string.tv);
    }

    private void displayMovieView(MovieResponse movieResponse) {
        Glide.with(this)
                .load(imgBaseUrl + movieResponse.getBackdropPath())
                .centerCrop()
                .into(backdrop_image_iv);
        Glide.with(this)
                .load(imgBaseUrl + movieResponse.getPosterPath())
                .centerCrop()
                .into(poster_image_iv);
        title_tv.setText(movieResponse.getTitle());
        release_date_tv.setText(movieResponse.getReleaseDate());
        rating_tv.setText(String.valueOf(movieResponse.getVoteAverage()));
        overview_tv.setText(movieResponse.getOverview());
        type_icon_iv.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.movie_icon, null));
        type_tv.setText(R.string.movie);
    }

    private void setView() {
        backdrop_image_iv = findViewById(R.id.backdrop_image_iv);
        poster_image_iv = findViewById(R.id.poster_image_iv);
        back_button = findViewById(R.id.back_button);
        favourite_button = findViewById(R.id.favourite_button);
        title_tv = findViewById(R.id.title_tv);
        release_date_tv = findViewById(R.id.release_date_tv);
        rating_tv = findViewById(R.id.rating_tv);
        overview_tv = findViewById(R.id.overview_tv);
        type_icon_iv = findViewById(R.id.type_icon_iv);
        type_tv = findViewById(R.id.type_tv);
    }
}