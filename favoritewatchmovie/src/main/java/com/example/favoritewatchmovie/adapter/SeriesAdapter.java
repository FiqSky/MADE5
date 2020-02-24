package com.example.favoritewatchmovie.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.favoritewatchmovie.R;
import com.example.favoritewatchmovie.db.FavoriteContract;
import com.squareup.picasso.Picasso;

public class SeriesAdapter extends CursorAdapter {

    public SeriesAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_movie, parent, false);
    }


    public Cursor getCursor() {
        return super.getCursor();
    }


    public void bindView(View view, final Context context, final Cursor cursor) {
        if (cursor != null) {
            TextView title;
            TextView overview;
            TextView relese;
            ImageView poster_path;

            title = view.findViewById(R.id.tvTitle);
            overview = view.findViewById(R.id.tvOverv);
            relese = view.findViewById(R.id.tvRelese);
            poster_path = view.findViewById(R.id.ivPoster_path);

            title.setText(FavoriteContract.getColumnString(cursor, FavoriteContract.SeriesColumns.TITLE_SERIES));
            overview.setText(FavoriteContract.getColumnString(cursor, FavoriteContract.SeriesColumns.OVERVIEW_SERIES));
            relese.setText(FavoriteContract.getColumnString(cursor, FavoriteContract.SeriesColumns.RELESE_DATE_SERIES));
            Picasso.get()
                    .load(FavoriteContract.getColumnString(cursor, FavoriteContract.SeriesColumns.POSTER_PATH_SERIES))
                    .placeholder(R.color.colorAccent)
                    .error(R.drawable.ic_image)
                    .into(poster_path);


        }
    }
}
