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
import com.farzain.watchmovie.R;
import com.farzain.watchmovie.Series;
import com.farzain.watchmovie.activity.SeriesInfoActivity;

import java.util.ArrayList;

public class ListSeriesAdapter extends RecyclerView.Adapter<ListSeriesAdapter.ListViewHolder> {
    private ArrayList<Series> listSeries = new ArrayList<>();

    public void setSeriesData(ArrayList<Series> seriesData) {
        listSeries.clear();
        listSeries.addAll(seriesData);
        notifyDataSetChanged();
    }

    private ListSeriesAdapter.OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(ListSeriesAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View seriesView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_series, viewGroup, false);
        return new ListViewHolder(seriesView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        holder.bind(listSeries.get(position));
    }

    @Override
    public int getItemCount() {
        return listSeries.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgPhoto;
        TextView tvName;
        TextView tvDescription;

        ListViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvDescription = itemView.findViewById(R.id.tv_item_description);

            itemView.setOnClickListener(this);
        }

        public void bind(Series series) {
            tvName.setText(series.getName());
            tvDescription.setText(series.getSynopsis());
            Glide.with(itemView.getContext())
                    .load(series.getPhoto())
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(imgPhoto);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Series dataseries = listSeries.get(position);
            dataseries.setName(dataseries.getName());
            dataseries.setSynopsis(dataseries.getSynopsis());

            Intent moveSeriesIntent = new Intent(itemView.getContext(), SeriesInfoActivity.class);
            moveSeriesIntent.putExtra(SeriesInfoActivity.EXTRA_SERIES, dataseries);
            itemView.getContext().startActivity(moveSeriesIntent);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Series dataSeries);
    }
}
