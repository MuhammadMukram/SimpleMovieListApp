package com.example.simplemovielistapp.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.simplemovielistapp.MovieTvDetailActivity;
import com.example.simplemovielistapp.R;
import com.example.simplemovielistapp.models.FavouriteModel;

import java.util.ArrayList;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder>{
    private final String imgBaseUrl = "https://image.tmdb.org/t/p/w500";
    private ArrayList<FavouriteModel> favouriteModels;
    private ActivityResultLauncher<Intent> resultLauncher;

    public FavouriteAdapter(ArrayList<FavouriteModel> favouriteModels, ActivityResultLauncher<Intent> resultLauncher) {
        this.favouriteModels = favouriteModels;
        this.resultLauncher = resultLauncher;
    }

    @NonNull
    @Override
    public FavouriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.linear_layout_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.ViewHolder holder, int position) {
        FavouriteModel favouriteModel = favouriteModels.get(position);
        holder.title_tv.setText(favouriteModel.getTitle());
        holder.release_year_tv.setText(favouriteModel.getDate().substring(0, 4));
        Glide.with(holder.itemView.getContext())
                .load(imgBaseUrl + favouriteModel.getPoster_path())
                .centerCrop()
                .placeholder(R.drawable.no_img)
                .into(holder.imageView);
        if (favouriteModel.getType() == MovieTvDetailActivity.TYPE_MOVIE) {
            holder.icon_type_iv.setImageDrawable(holder.itemView.getContext().getDrawable(R.drawable.movie_icon));
        } else {
            holder.icon_type_iv.setImageDrawable(holder.itemView.getContext().getDrawable(R.drawable.tv_icon));
        }
        holder.item_cv.setOnClickListener(v -> {
            Intent i = new Intent(holder.itemView.getContext(), MovieTvDetailActivity.class);
            i.putExtra(MovieTvDetailActivity.EXTRA_ITEM, favouriteModel);
            i.putExtra(MovieTvDetailActivity.EXTRA_TYPE, favouriteModel.getType());
            resultLauncher.launch(i);
        });
    }

    @Override
    public int getItemCount() {
        return favouriteModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, icon_type_iv;
        TextView title_tv, release_year_tv;
        CardView item_cv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            icon_type_iv = itemView.findViewById(R.id.icon_type_iv);
            title_tv = itemView.findViewById(R.id.title_tv);
            release_year_tv = itemView.findViewById(R.id.release_year_tv);
            item_cv = itemView.findViewById(R.id.item_cv);
        }
    }
}
