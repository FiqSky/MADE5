package com.example.favoritewatchmovie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.favoritewatchmovie.R;
import com.example.favoritewatchmovie.model.Fav;

import java.util.ArrayList;
import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavViewHolder> {
    private List<Fav> fav;
    private Context context;
//    private static final String IMAGE_URL = "http://image.tmdb.org/t/p/w185";

    public FavAdapter(Context context){
    this.context = context;
    fav = new ArrayList<>();
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem());
        }
    }

    private void remove(Fav favorite) {
        int position = fav.indexOf(favorite);
        if (position > -1) {
            fav.remove(position);
            notifyItemRemoved(position);
        }
    }

    private Fav getItem() {
        return fav.get(0);
    }

    @NonNull
    @Override
    public FavAdapter.FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_fav, parent, false);
        return new FavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavAdapter.FavViewHolder holder, int position) {
        final Fav favorite = fav.get(position);
        holder.bind(favorite);
    }

    @Override
    public int getItemCount() {
        return fav.size();
    }

    public class FavViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView overview;
        private ImageView poster_path;

        public FavViewHolder(@NonNull View itemView) {
            super(itemView);
            title =itemView.findViewById(R.id.tv_item_name);
            overview = itemView.findViewById(R.id.tv_item_description);
            poster_path = itemView.findViewById(R.id.img_item_photo);
        }
        public void bind(Fav favorite) {
            title.setText(favorite.getTitle());
            overview.setText(favorite.getOverview());

            Glide.with(itemView.getContext())
                    .load(favorite.getPoster_path())
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(poster_path);
        }
    }
}
