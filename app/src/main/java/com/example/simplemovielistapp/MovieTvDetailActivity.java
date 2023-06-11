package com.example.simplemovielistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.content.ContentValues;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.simplemovielistapp.models.FavouriteModel;
import com.example.simplemovielistapp.models.MovieResponse;
import com.example.simplemovielistapp.models.TvResponse;
import com.example.simplemovielistapp.sqlitesection.DatabaseContract;
import com.example.simplemovielistapp.sqlitesection.FavouriteHelper;

public class MovieTvDetailActivity extends AppCompatActivity {
    public static final String EXTRA_ITEM = "item_extra";
    public static final String EXTRA_TYPE = "type_extra";
    public static final int TYPE_MOVIE = 1;
    public static final int TYPE_TV = 2;
    private boolean isFavourite = false;
    private final String imgBaseUrl = "https://image.tmdb.org/t/p/w500";
    private ImageView backdrop_image_iv, poster_image_iv, type_icon_iv, favourite_icon_iv;
    private CardView back_button, favourite_button;
    private TextView title_tv, release_date_tv, rating_tv, overview_tv, type_tv;
    private FavouriteHelper favouriteHelper;
    private FavouriteModel favouriteModel = new FavouriteModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_tv_detail);
        setView();

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Detail");

        favouriteHelper = FavouriteHelper.getInstance(getApplicationContext());
        favouriteHelper.open();

        back_button.setOnClickListener(v -> {
            setResult(RESULT_OK, getIntent());
            finish();
        });


        favouriteModel = getIntent().getParcelableExtra(EXTRA_ITEM);

        displayData(favouriteModel);

        isFavourite = favouriteHelper.getFavouriteStatus(String.valueOf(favouriteModel.getId())) > 0;

        if (isFavourite) favourite_icon_iv.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.favorite_fill_icon, null));
        else favourite_icon_iv.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.favorite_border_icon, null));

        favourite_button.setOnClickListener(v -> {
            isFavourite = !isFavourite;
            if (isFavourite) {
                favourite_icon_iv.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.favorite_fill_icon, null));
                insertDataToFavouriteTable(favouriteModel);
            } else {
                favourite_icon_iv.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.favorite_border_icon, null));
                deleteDataToFavouriteTable(favouriteModel.getId());
            }
        });
    }

    private void deleteDataToFavouriteTable(int id) {
        long result = favouriteHelper.deleteData(String.valueOf(id));

        String message = result > 0 ? "Berhasil mengeluarkan " + favouriteModel.getTitle() + " dari favourite list" : "Gagal mengeluarkan " + favouriteModel.getTitle() + " dari favourite list";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void insertDataToFavouriteTable(FavouriteModel favouriteModel) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.ItemColumns._ID, favouriteModel.getId());
        values.put(DatabaseContract.ItemColumns.TITLE, favouriteModel.getTitle());
        values.put(DatabaseContract.ItemColumns.DATE, favouriteModel.getDate());
        values.put(DatabaseContract.ItemColumns.VOTE_AVERAGE, favouriteModel.getVote_average());
        values.put(DatabaseContract.ItemColumns.OVERVIEW, favouriteModel.getOverview());
        values.put(DatabaseContract.ItemColumns.POSTER_PATH, favouriteModel.getPoster_path());
        values.put(DatabaseContract.ItemColumns.BACKDROP_PATH, favouriteModel.getBackdrop_path());
        values.put(DatabaseContract.ItemColumns.TYPE, favouriteModel.getType());

        long result = favouriteHelper.insertData(values);

        String message = result > 0 ? "Berhasil menambahkan " + favouriteModel.getTitle() + " dari favourite list" : "Gagal menambahkan " + favouriteModel.getTitle() + " dari favourite list";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void displayData(FavouriteModel favouriteModel) {
        Glide.with(this)
                .load(imgBaseUrl + favouriteModel.getBackdrop_path())
                .centerCrop()
                .placeholder(R.drawable.no_img)
                .into(backdrop_image_iv);
        Glide.with(this)
                .load(imgBaseUrl + favouriteModel.getPoster_path())
                .centerCrop()
                .placeholder(R.drawable.no_img)
                .into(poster_image_iv);

        if (favouriteModel.getTitle().equals("") || favouriteModel.getTitle() == null)
            title_tv.setText("-");
        else
            title_tv.setText(favouriteModel.getTitle());

        if (favouriteModel.getDate().equals("") || favouriteModel.getDate() == null)
            release_date_tv.setText("-");
        else
            release_date_tv.setText(favouriteModel.getDate());

        if (favouriteModel.getVote_average().equals("0") || favouriteModel.getVote_average() == null)
            rating_tv.setText("-");
        else
            rating_tv.setText(String.valueOf(favouriteModel.getVote_average()));

        if (favouriteModel.getOverview().equals("") || favouriteModel.getOverview() == null)
            overview_tv.setText("-");
        else
            overview_tv.setText(favouriteModel.getOverview());

        if (favouriteModel.getType() == TYPE_MOVIE) {
            type_icon_iv.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.movie_icon, null));
            type_tv.setText(R.string.movie);

        } else {
            type_icon_iv.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.tv_icon, null));
            type_tv.setText(R.string.tv);
        }

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
        favourite_icon_iv = findViewById(R.id.favourite_icon_iv);
        type_tv = findViewById(R.id.type_tv);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK, getIntent());
        finish();
        super.onBackPressed();
    }
}