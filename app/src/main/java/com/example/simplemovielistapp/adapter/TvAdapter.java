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
import com.example.simplemovielistapp.models.TvResponse;

import java.util.List;
import java.util.Objects;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.ViewHolder> {
    private List<TvResponse> dataTv;
    private String imgBaseUrl = "https://image.tmdb.org/t/p/w500";

    public TvAdapter(List<TvResponse> dataTv) {
        this.dataTv = dataTv;
    }

    @NonNull
    @Override
    public TvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.grid_layout_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvAdapter.ViewHolder holder, int position) {
        TvResponse tvResponse = dataTv.get(position);

        if (tvResponse.getName().equals("") || tvResponse.getName() == null)
            holder.title_tv.setText("-");
        else
            holder.title_tv.setText(tvResponse.getName());

        if (Objects.equals(tvResponse.getFirstAirDate(), "") || tvResponse.getFirstAirDate() == null)
            holder.release_date_tv.setText("-");
        else
            holder.release_date_tv.setText(tvResponse.getFirstAirDate().substring(0, 4));

        Glide.with(holder.itemView.getContext())
                .load(imgBaseUrl + tvResponse.getPosterPath())
                .centerCrop()
                .placeholder(R.drawable.no_img)
                .into(holder.movie_poster_iv);

        holder.item_grid_cv.setOnClickListener(v -> {
            FavouriteModel favouriteModel = new FavouriteModel(
                    tvResponse.getId(),
                    tvResponse.getName(),
                    tvResponse.getFirstAirDate(),
                    tvResponse.getOverview(),
                    tvResponse.getPosterPath(),
                    tvResponse.getBackdropPath(),
                    tvResponse.getVoteAverage(),
                    MovieTvDetailActivity.TYPE_TV );

            Intent i = new Intent(holder.itemView.getContext(), MovieTvDetailActivity.class);
            i.putExtra(MovieTvDetailActivity.EXTRA_ITEM, favouriteModel);
            holder.itemView.getContext().startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return dataTv.size();
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

