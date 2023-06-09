package com.example.simplemovielistapp.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.simplemovielistapp.MovieTvDetailActivity;
import com.example.simplemovielistapp.R;
import com.example.simplemovielistapp.models.FavouriteModel;
import com.example.simplemovielistapp.models.MovieResponse;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    private final String imgBaseUrl = "https://image.tmdb.org/t/p/w500";
    private List<MovieResponse> dataMovie;

    public MovieAdapter(List<MovieResponse> dataMovie) {
        this.dataMovie = dataMovie;
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.grid_layout_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieResponse movieResponse = dataMovie.get(position);
        holder.title_tv.setText(movieResponse.getTitle());
        holder.release_date_tv.setText(movieResponse.getReleaseDate().substring(0, 4));
        Glide.with(holder.itemView.getContext())
                .load(imgBaseUrl + movieResponse.getPosterPath())
                .centerCrop()
                .placeholder(R.drawable.no_img)
                .into(holder.movie_poster_iv);
        holder.item_grid_cv.setOnClickListener(v -> {
            FavouriteModel favouriteModel = new FavouriteModel(
                    movieResponse.getId(),
                    movieResponse.getTitle(),
                    movieResponse.getReleaseDate(),
                    movieResponse.getOverview(),
                    movieResponse.getPosterPath(),
                    movieResponse.getBackdropPath(),
                    movieResponse.getVoteAverage(),
                    MovieTvDetailActivity.TYPE_MOVIE );

            Intent i = new Intent(holder.itemView.getContext(), MovieTvDetailActivity.class);
            i.putExtra(MovieTvDetailActivity.EXTRA_ITEM, favouriteModel);
            holder.itemView.getContext().startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return dataMovie.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView movie_poster_iv;
        TextView title_tv, release_date_tv;
        CardView item_grid_cv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movie_poster_iv = itemView.findViewById(R.id.movie_poster_iv);
            title_tv = itemView.findViewById(R.id.title_tv);
            release_date_tv = itemView.findViewById(R.id.release_year_tv);
            item_grid_cv = itemView.findViewById(R.id.item_grid_cv);
        }

    }
}
