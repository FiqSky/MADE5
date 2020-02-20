package com.farzain.watchmovie.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.farzain.watchmovie.Movie;
import com.farzain.watchmovie.R;
import com.farzain.watchmovie.activity.MovieInfoActivity;

import java.util.ArrayList;

public class ListMovieAdapter extends RecyclerView.Adapter<ListMovieAdapter.ListViewHolder> {
    private ArrayList<Movie> listMovie = new ArrayList<>();

    public void setData(ArrayList<Movie> data) {
        listMovie.clear();
        listMovie.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_movie, viewGroup, false);
        return new ListViewHolder(view);
    }

    private ListMovieAdapter.OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(ListMovieAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        holder.bind(listMovie.get(position));
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgPhoto;
        TextView tvName;
        TextView tvDescription;

        ListViewHolder(View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvDescription = itemView.findViewById(R.id.tv_item_description);

            itemView.setOnClickListener(this);
        }

        void bind(Movie movie) {
            tvName.setText(movie.getName());
            tvDescription.setText(movie.getSynopsis());

            Glide.with(itemView.getContext())
                    .load(movie.getPhoto())
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(imgPhoto);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            Movie datamovie = listMovie.get(position);
            datamovie.setName(datamovie.getName());
            datamovie.setSynopsis(datamovie.getSynopsis());
            datamovie.setPhoto(datamovie.getPhoto());
            datamovie.setRelease(datamovie.getRelease());

            Intent moveIntent = new Intent(itemView.getContext(), MovieInfoActivity.class);
            moveIntent.putExtra(MovieInfoActivity.EXTRA_MOVIE, datamovie);
            itemView.getContext().startActivity(moveIntent);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Movie data);
    }
}
